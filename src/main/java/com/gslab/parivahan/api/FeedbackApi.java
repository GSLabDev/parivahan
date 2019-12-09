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

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gslab.parivahan.dbmodel.ParivahanFeedback;
import com.gslab.parivahan.model.FeedbackVo;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.util.RideConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "FeedBackApi", description = "Feedback about parivahan platform")
@RequestMapping("/api")
public interface FeedbackApi {

	@ApiOperation(value = "FeedBack Api", nickname = "FeedBack Api", notes = "Api will Post Feedback about parivahan platform", response = FeedbackVo.class, responseContainer = "List", tags = {
			"feedback", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = FeedbackVo.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error") })
	@RequestMapping(value=RideConstants.VERSION_1+"/feedback",produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.POST)
	ResponseEntity<FeedbackVo> giveFeedback(@ApiParam(value = "Feedback", required = true) @Valid @RequestBody FeedbackVo feedbackVO);

	@ApiOperation(value = "GetFeedBackByEmailId", nickname = "GetFeedBackByEmailId", notes = "Api will Get feedback given by a user about parivahan platform", response = FeedbackVo.class, responseContainer = "List", tags = {
			"feedback", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = FeedbackVo.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error") })
	@RequestMapping(value=RideConstants.VERSION_1+"/feedback/user/{email}",produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<FeedbackVo>> getFeedbackByEmailId(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "email", required = true) @Valid @PathVariable("email") String email);

	@ApiOperation(value = "GetFeedBackByRating", nickname = "GetFeedBackByRating", notes = "Api will Get feedback given by a Ratings about parivahan platform", response = FeedbackVo.class, responseContainer = "List", tags = {
			"feedback", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = FeedbackVo.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error") })
	@RequestMapping(value=RideConstants.VERSION_1+"/feedback/rating/{rating}",produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<FeedbackVo>> getFeedbackRatings(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "2" , required = true) @Valid @PathVariable("rating") int rating);

	// --- TO-DO API --
	@ApiOperation(value = "RecentFeedBack", nickname = "RecentFeedBack", notes = "Api will Recent Feedback about parivahan platform", response = FeedbackVo.class, responseContainer = "List", tags = {
			"feedback", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = FeedbackVo.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error") })
	@RequestMapping(value=RideConstants.VERSION_1+"/feedback/recent",produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<FeedbackVo>> getRecentFeedback(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType);

	
	// --- TO-DO API --
	@ApiOperation(value = "SortedFeedback", nickname = "SortedFeedback", notes = "Api will Sorted Feedback about parivahan platform", response = FeedbackVo.class, responseContainer = "List", tags = {
			"feedback", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = FeedbackVo.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error") })
	@RequestMapping(value="/v1/feedback/rating",produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<FeedbackVo>> getFeedbackSortedByRatings(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType);
	
}

