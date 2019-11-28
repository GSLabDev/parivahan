package com.gslab.parivahan.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.gslab.parivahan.security.IParivahanJwtToken;

public class ParivahanTokenExpiredException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7213931431877683601L;

	 private IParivahanJwtToken token;
	 
	public ParivahanTokenExpiredException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public ParivahanTokenExpiredException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}


	 public String token() {
	        return this.token.getToken();
	    }
	
}
