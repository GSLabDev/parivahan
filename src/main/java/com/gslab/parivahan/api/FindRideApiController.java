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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.websocket.server.PathParam;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.model.LdapUser;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideParams;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.model.RideSearchCriteria;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.service.IDirectionsService;
import com.gslab.parivahan.service.UserService;

import io.swagger.annotations.ApiParam;

@Controller
public class FindRideApiController implements FindRideApi {

	@Autowired
	private IDirectionsService directionsService;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private Environment env;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public FindRideApiController(final ObjectMapper objectMapper, final HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public ResponseEntity<List<RideSearch>> joinRides(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = false) final String contentType,
			@ApiParam(value = "Start location of Ride") @Valid @RequestParam(value = "startLocation", required = true) final String startLocation,
			@ApiParam(value = "End Location of Ride") @Valid @RequestParam(value = "endLocation", required = true) final String endLocation,
			@ApiParam(value = "Current GPS Location of rider") @Valid @RequestParam(value = "currentGPSLocation", required = false) final String currentGPSLocation,
			@ApiParam(value = "Currnecy of country") @Valid @RequestParam(value = "currency", required = false) final String currency,
			@ApiParam(value = "Start co-ordinate") @Valid @RequestParam(value = "startCoordinate", required = false) final String startCoordinate,
			@ApiParam(value = "End co-ordinate") @Valid @RequestParam(value = "endrtCoordinate", required = false) final String endrtCoordinate,
			@ApiParam(value = "Date of Start journey") @Valid @RequestParam(value = "date", required = false) final String date,
			@ApiParam(value = "Time to Start journey") @Valid @RequestParam(value = "Hours", required = false) final String hours,
			@ApiParam(value = "Page number to search", defaultValue = "1") @Valid @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
			@Min(1) @ApiParam(value = "Number of seats to search for ride", defaultValue = "1") @Valid @RequestParam(value = "seats", required = false, defaultValue = "1") final Integer seats,
			@ApiParam(value = "Does photo of driver is filter parameter", defaultValue = "false") @Valid @RequestParam(value = "photo", required = false, defaultValue = "false") final Boolean photo,
			@ApiParam(value = "Sort rides by Date, nearest date first", defaultValue = "true") @Valid @RequestParam(value = "sort", required = false, defaultValue = "true") final Boolean sort,
			@ApiParam(value = "Order rides as per charges, Lowest price first", defaultValue = "true") @Valid @RequestParam(value = "order", required = false, defaultValue = "true") final Boolean order,
			@ApiParam(value = "Radius from current location to search rides in Meters", defaultValue = "0") @Valid @RequestParam(value = "radius", required = false, defaultValue = "0") final Integer radius,
			@ApiParam(value = "Radius from Start Location to search rides in Meters", defaultValue = "0") @Valid @RequestParam(value = "radiusFromStartLocation", required = false, defaultValue = "0") final Integer radiusFromStartLocation,
			@ApiParam(value = "Radius from End Location to search rides in Meters", defaultValue = "5") @Valid @RequestParam(value = "radiusFromEndLocation", required = false, defaultValue = "5") final Integer radiusFromEndLocation,
			@ApiParam(value = "Minimum price from which search will start", defaultValue = "0") @Valid @RequestParam(value = "minPrice", required = false, defaultValue = "0") final Integer minPrice,
			@ApiParam(value = "Maximum pricee for searching rides", defaultValue = "5000") @Valid @RequestParam(value = "maxPrice", required = false, defaultValue = "5000") final Integer maxPrice,
			@ApiParam(value = "Email id of user") @RequestParam(value = "email",required =false) String email) {
		
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
		ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		UserVO user = userService.getUserByUsername(usercontext.getUsername());
		if(null != user) {
			email = user.getEmail();
		}
		}
		return new ResponseEntity<List<RideSearch>>(directionsService.searchRides(
				new RideSearchCriteria(startLocation, endLocation, radius, false, date, hours,email)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RideSearch> getRideDetails(
			@ApiParam(value = "", required = true) @PathVariable("rideid") String rideid,
			@ApiParam(value = "ride details", required = true) @Valid @RequestBody RideParams rideParams) {
		return new ResponseEntity<RideSearch>(directionsService.getFilteredRidesWithParams(rideid, rideParams),
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<Ride>> getShuttleDetails(@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = false) final String contentType,@ApiParam(value = "", required = true) @PathVariable("date") String date){
		return new ResponseEntity<List<Ride>>(directionsService.getShuttleByDate(date),HttpStatus.OK);
	}

}