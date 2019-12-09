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
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.util.RideConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
public interface UserProfileApi {

	@ApiOperation(value = "get shuttle Details", nickname = "getShuttleDetails", notes = "Api will Return shuttle details", response = RideSearch.class, tags = {
			"developers", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK.", response = RideSearch.class),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Server, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/booking", produces = { "application/json" }, method = RequestMethod.GET)
	List<RideRequestVO> getUserBookings(@RequestParam(required=false) String type,@RequestParam(required=false) String email);
	
	@ApiOperation(value = "get shuttle Details", nickname = "getShuttleDetails", notes = "Api will Return shuttle details", response = RideSearch.class, tags = {
			"developers", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK.", response = RideSearch.class),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Server, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/offer", produces = { "application/json" }, method = RequestMethod.GET)
	List<Ride> getUserOffers(@RequestParam(required=false) String email);

	Map<String, Object> getAllowedEmailSuffix();
}
