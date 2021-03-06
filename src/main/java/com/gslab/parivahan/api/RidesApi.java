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
/**
 * NOTE: This class is auto generated by the swagger code generator program (1.0.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.gslab.parivahan.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.util.RideConstants;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Api(value = "rides", description = "the rides API")
@RequestMapping("/api")
public interface RidesApi {

	@ApiOperation(value = "getAllRides", nickname = "getAllRides", notes = "get All rides", response = Ride.class, responseContainer = "List", tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/ride", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<Ride>> getAllRides(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);

	@ApiOperation(value = "getRecentRides", nickname = "getRecentRides", notes = "get Recent rides", response = Ride.class, responseContainer = "List", tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/ride/recent", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<Ride>> getRecentRides(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType,
			@ApiParam(value = "", required = true) @RequestParam int pageSize,
			@ApiParam(value = "", required = true) @RequestParam int startIndex,
			@ApiParam(value = "", required = false) @RequestParam String email);

	@ApiOperation(value = "CountRides", nickname = "CountRides", notes = "get ride count", response = Ride.class, responseContainer = "List", tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request."), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/ride/count", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Integer> countTotalRides(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);
}
