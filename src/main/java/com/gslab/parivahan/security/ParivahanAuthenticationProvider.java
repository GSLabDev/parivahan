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
package com.gslab.parivahan.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.gslab.parivahan.model.ParivahanUserContext;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */

@Component
public class ParivahanAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		String ldapServerUrl =  ParivahanLdapAuthSettings.getUrls()+ParivahanLdapAuthSettings.getBasedn();
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapServerUrl);
		String ldapManagerDn =  ParivahanLdapAuthSettings.getUsername();
		contextSource.setUserDn(ldapManagerDn);
		String ldapManagerPassword = ParivahanLdapAuthSettings.getPassword();
		contextSource.setPassword(ldapManagerPassword);
		contextSource.afterPropertiesSet();
		LdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch("",
				"(&(objectClass=user)(" + ParivahanLdapAuthSettings.getUserdnpattern() + "))", contextSource);
		BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
		bindAuthenticator.setUserSearch(ldapUserSearch);
		DirContextOperations user = bindAuthenticator.authenticate(token);

		if (user == null) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		ParivahanUserContext userContext = new ParivahanUserContext(username,null);
		return new UsernamePasswordAuthenticationToken(userContext, null, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	
}
