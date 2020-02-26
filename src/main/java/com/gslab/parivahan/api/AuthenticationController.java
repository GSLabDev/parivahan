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
package com.gslab.parivahan.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gslab.parivahan.model.ParivahanUserDto;
import com.gslab.parivahan.util.RideConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	
	@Autowired
	private Environment env;

	/**
	 * Implemented by Spring Security
	 */
	@ApiOperation(value = "Login", notes = "Login with the given credentials.")
	@ApiResponses({ @ApiResponse(code = 200, message = "", response = Authentication.class) })
	@RequestMapping(value = RideConstants.VERSION_1+"/auth/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void springSecurityLogin(@RequestBody ParivahanUserDto user,
			@RequestHeader(value = "X-Requested-With", defaultValue = "XMLHttpRequest") String requestedWith) {
		throw new IllegalStateException(
				"This method shouldn't be called. It's implemented by spring security filters.");
	}

	/**
	 * Implemented by Spring Security
	 */
	@ApiOperation(value = "Logout", notes = "Logout the current user.")
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = RideConstants.VERSION_1+"/auth/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void springSecurityLogout() {
		throw new IllegalStateException(
				"This method shouldn't be called. It's implemented by spring security filters.");
	}
	
	
	@ApiOperation(value = "isLDAPEnabled", notes = "Is ldap enabled.")
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = RideConstants.VERSION_1+"/ldap/enable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String isLDAPEnabled() {
		return env.getProperty("parivahan.auth.ldap.enabled");
	}
}
