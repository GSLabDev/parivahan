/*
 * Copyright (c) 2003-2019, Great Software Laboratory Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gslab.parivahan.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.security.IParivahanTokenExtractor;
import com.gslab.parivahan.security.ParivahanAuthenticationProvider;
import com.gslab.parivahan.security.ParivahanJwtAuthenticationProvider;
import com.gslab.parivahan.security.ParivahanJwtTokenAuthenticationProcessingFilter;
import com.gslab.parivahan.security.ParivahanLoginProcessingFilter;
import com.gslab.parivahan.security.SkipPathRequestMatcher;
import com.gslab.parivahan.util.RideConstants;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ParivahanWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String AUTHENTICATION_URL = "/api/v1/auth/login";
	public static final String REFRESH_TOKEN_URL = "/api/v1/auth/token";
	public static final String API_ROOT_URL = "/api/**";

	@Autowired
	UserDetailsContextMapper userContextMapper;
	
	@Autowired
	ParivahanAuthenticationEntryPoint parivahanAuthenticationEntryPoint;
	
	@Autowired
	ParivahanAuthenticationProvider provider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private IParivahanTokenExtractor tokenExtractor;
	
	@Autowired
	private ParivahanJwtAuthenticationProvider jwtProvider;

	@Autowired
	private Environment env;
	
	protected ParivahanLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
ParivahanLoginProcessingFilter filter = new ParivahanLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler,
				objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}		
	
	protected ParivahanJwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
			List<String> pathsToSkip, String pattern) throws Exception {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
		ParivahanJwtTokenAuthenticationProcessingFilter filter = new ParivahanJwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
		auth.authenticationProvider(jwtProvider);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html#!/", "/webjars/**","/swagger-ui.html/","/api/v1/ldap/enable");

	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(AUTHENTICATION_URL,REFRESH_TOKEN_URL);
		http.csrf().disable();
		
		if(env.getProperty("parivahan.auth.ldap.enabled").equalsIgnoreCase("true")) {
		
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(this.parivahanAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()])).permitAll().and()
				.authorizeRequests()
				.antMatchers(API_ROOT_URL)
				.authenticated().and()
				.addFilterBefore(new SimpleCORSFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,API_ROOT_URL),
						UsernamePasswordAuthenticationFilter.class);
	      }
	}

}
