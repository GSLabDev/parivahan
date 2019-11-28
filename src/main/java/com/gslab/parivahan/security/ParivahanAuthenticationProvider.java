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
