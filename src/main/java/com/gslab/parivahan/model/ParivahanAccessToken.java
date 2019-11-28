 package com.gslab.parivahan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gslab.parivahan.security.IParivahanJwtToken;

import io.jsonwebtoken.Claims;

public class ParivahanAccessToken implements IParivahanJwtToken {

	  private final String rawToken;
	    @JsonIgnore private Claims claims;

	    public ParivahanAccessToken(final String token, Claims claims) {
	        this.rawToken = token;
	        this.claims = claims;
	    }

	    public String getToken() {
	        return this.rawToken;
	    }

	    public Claims getClaims() {
	        return claims;
	    }
}
