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
