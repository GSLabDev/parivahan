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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.exceptions.RideRequestNotFoundException;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.service.IDirectionsService;
import com.gslab.parivahan.service.IRideRequestService;
import com.gslab.parivahan.service.UserService;

import io.swagger.annotations.ApiParam;

@Controller
public class RideRequestApiController implements RideRequestApi {

	@Autowired
	private IRideRequestService rideRequestService;

	@Autowired
	private IDirectionsService directionsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;

	private HttpServletRequest request;

	public ResponseEntity<RideRequestVO> addRideRequest(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestBody RideRequestVO rideRequest,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType) {
		RideRequestVO ride = new RideRequestVO();
		String email = null;
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
			ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal());
			email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		} else {
			email = rideRequest.getUserEmail();
		}
		if (StringUtils.isBlank(email))
			return null;
		rideRequest.setUserEmail(email);
		ride = rideRequestService.addRideRequest(rideRequest);
		return new ResponseEntity<RideRequestVO>(ride, HttpStatus.OK);
	}

	public ResponseEntity<List<RideRequest>> getAllRidesRequests() {
		List<RideRequest> rides = new ArrayList<RideRequest>();
		HttpStatus status = null;
		try {
			rides = rideRequestService.getAllRideRequest();
			status = HttpStatus.OK;
		} catch (RideRequestNotFoundException e) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<List<RideRequest>>(rides, status);
	}

	public ResponseEntity<RideRequestVO> getRideRequestsByBookingCode(@PathVariable Integer bookingCode,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType) {
		RideRequestVO rideRequestVO = new RideRequestVO();
		HttpStatus status = null;
		try {
			rideRequestVO = rideRequestService.getRideRequestByBookingCode(bookingCode);
			status = HttpStatus.OK;
		} catch (RideRequestNotFoundException e) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<RideRequestVO>(rideRequestVO, status);
	}

	public ResponseEntity<Ride> getRideRequestsByOfferCode(@PathVariable Integer offerCode,@RequestParam(value = "email",required=false) String email,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType) {
		Ride ride = null;
		HttpStatus status = null;
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
		ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		}
		if (StringUtils.isBlank(email))
			return null;
		try {
			ride = directionsService.getRideWithRequests(offerCode,email);
			status = HttpStatus.OK;
		} catch (RideRequestNotFoundException e) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Ride>(ride, status);
	}

	public ResponseEntity<RideRequestVO> updateRideRequestStatus(@RequestParam(required = true) String rideStatus,
			@PathVariable String bookingId,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType) {
		RideRequestVO ride = new RideRequestVO();
		HttpStatus status = null;
		try {
			ride = rideRequestService.updateRideRequestStatus(rideStatus, bookingId,null);
			status = HttpStatus.OK;
		} catch (RideRequestNotFoundException e) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<RideRequestVO>(ride, status);
	}
}
