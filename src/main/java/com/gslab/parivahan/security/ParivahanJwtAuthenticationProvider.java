package com.gslab.parivahan.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gslab.parivahan.model.ParivahanJwtAuthenticationToken;
import com.gslab.parivahan.model.ParivahanUserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */
@Component
public class ParivahanJwtAuthenticationProvider  implements AuthenticationProvider {

	 private final ParivahanJwtSettings jwtSettings;
	    
	    @Autowired
	    public ParivahanJwtAuthenticationProvider(ParivahanJwtSettings jwtSettings) {
	        this.jwtSettings = jwtSettings;
	    }

	    @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    	ParivahanRawAccessJwtToken rawAccessToken = (ParivahanRawAccessJwtToken) authentication.getCredentials();
	        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
	        String subject = jwsClaims.getBody().getSubject(); 
	        
	        ParivahanUserContext context = ParivahanUserContext.create(subject, null);
	        
	        return new ParivahanJwtAuthenticationToken(context, null);
	    } 

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return (ParivahanJwtAuthenticationToken.class.isAssignableFrom(authentication));
	    }
}
