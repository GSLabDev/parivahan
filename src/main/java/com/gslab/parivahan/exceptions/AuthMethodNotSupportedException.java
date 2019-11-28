package com.gslab.parivahan.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5942449318851480768L;

	public AuthMethodNotSupportedException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
