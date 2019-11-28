package com.gslab.parivahan.api;

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
}
