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
package com.gslab.parivahan.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gslab.parivahan.dbmodel.ParivahanFeedback;


public class FeedbackVo {
	
	private String userEmail;
	
	private int rating;
	
	private String comment;
	
	private String operation;

	
	public FeedbackVo(String userEmail, int rating, String comment,String operation) {
		super();
		this.userEmail =userEmail ;
		this.rating = rating;
		this.comment = comment;
		this.operation = operation;
	}
	
	public FeedbackVo(ParivahanFeedback feedback) {
		super();
		this.userEmail = feedback.getUserEmail();
		this.rating = feedback.getRating();
		this.comment = feedback.getComment();
		this.operation = feedback.getOperation();
	}

	
	public FeedbackVo() {
		super();
	}

	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
