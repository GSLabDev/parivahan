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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gslab.parivahan.model.ParivahanAccessToken;
import com.gslab.parivahan.model.ParivahanUserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */

@Component
public class ParivahanJwtTokenFactory {

	private final ParivahanJwtSettings settings;
	@Autowired
	public ParivahanJwtTokenFactory(ParivahanJwtSettings settings) {
		this.settings = settings;
	}
	
	public ParivahanAccessToken createAcccessToken(ParivahanUserContext userContext) {
		if (StringUtils.isBlank(userContext.getUsername()))
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		
		
		LocalDateTime currentTime = LocalDateTime.now();
		int expirationTime = 0;
		expirationTime = settings.getTokenExpirationTime();
		
		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		
	    //claims.put("scopes", "ROLE_USER");
		String token = Jwts.builder().setClaims(claims).setIssuer(settings.getTokenIssuer())
				.setSubject(userContext.getUsername())
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(
						Date.from(currentTime.plusMinutes(expirationTime).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey()).compact();

		return new ParivahanAccessToken(token, claims);
	}
}
