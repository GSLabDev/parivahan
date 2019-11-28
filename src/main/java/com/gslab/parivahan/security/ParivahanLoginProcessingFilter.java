package com.gslab.parivahan.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.exceptions.AuthMethodNotSupportedException;
import com.gslab.parivahan.model.ParivahanLoginRequest;
import com.gslab.parivahan.util.ParivahanWebUtil;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */
public class ParivahanLoginProcessingFilter  extends AbstractAuthenticationProcessingFilter  {
	
	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;
	private final ObjectMapper objectMapper;

	
	public ParivahanLoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, 
	            AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
	        super(defaultProcessUrl);
	        this.successHandler = successHandler;
	        this.failureHandler = failureHandler;
	        this.objectMapper = mapper;
	    }
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		if (!HttpMethod.POST.name().equals(request.getMethod()) || !ParivahanWebUtil.isAjax(request)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication method not supported. Request method: " + request.getMethod());
			}
			throw new AuthMethodNotSupportedException("Authentication method not supported");
		}

		ParivahanLoginRequest loginRequest = objectMapper.readValue(request.getReader(), ParivahanLoginRequest.class);
		if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
			throw new AuthenticationServiceException("Username or Password not provided");
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
		ParivahanAuthenticationProvider provider = new ParivahanAuthenticationProvider();
		return provider.authenticate(token);
	}

	 @Override 
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	            Authentication authResult) throws IOException, ServletException {
	        successHandler.onAuthenticationSuccess(request, response, authResult);
	    }

	    @Override
	    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException failed) throws IOException, ServletException {
	        SecurityContextHolder.clearContext();
	        failureHandler.onAuthenticationFailure(request, response, failed);
	    }
}
