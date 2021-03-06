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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.gslab.parivahan.dbmodel.ParivahanFeedback;
import com.gslab.parivahan.model.FeedbackVo;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.service.FeedbackService;

import io.swagger.annotations.ApiParam;

@RestController
public class FeedbackController implements FeedbackApi{

	@Autowired
	private FeedbackService feedbackService;
	@Override
	public ResponseEntity<FeedbackVo> giveFeedback(@RequestBody FeedbackVo feedbackVO) {
		return new ResponseEntity<FeedbackVo>(feedbackService.postFeedback(feedbackVO),HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<FeedbackVo>> getFeedbackByEmailId(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType,String email) {
		return new ResponseEntity<List<FeedbackVo>>(feedbackService.getFeedbackByEmailId(email),HttpStatus.OK);		  
	}
	
	@Override
	public ResponseEntity<List<FeedbackVo>> getFeedbackRatings(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType,int rating) {
		return new ResponseEntity<List<FeedbackVo>>(feedbackService.getFeedbackByRatings(rating),HttpStatus.OK);		  
	}
	
	@Override
	public ResponseEntity<List<FeedbackVo>> getRecentFeedback(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType) {
		return new ResponseEntity<List<FeedbackVo>>(feedbackService.getRecentFeedback(),HttpStatus.OK);		  
	}
	
	@Override
	public ResponseEntity<List<FeedbackVo>> getFeedbackSortedByRatings(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType) {
		return new ResponseEntity<List<FeedbackVo>>(feedbackService.getFeedbackSortedByRatings(),HttpStatus.OK);		  
	}

}
