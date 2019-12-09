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
