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
package com.gslab.parivahan.dbmodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.validation.annotation.Validated;

import com.gslab.parivahan.model.FeedbackVo;


@Validated

@Entity
@Table(name = "feedback")
public class ParivahanFeedback extends ParivahanAudingInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userEmail;
	
	private int rating;
	
	private String comment;
	
	private String operation;

	public ParivahanFeedback(FeedbackVo vo) {
		this.userEmail = vo.getUserEmail();
		this.rating = vo.getRating();
		this.comment = vo.getComment();
		this.operation = vo.getOperation();
	}
	
	
	public ParivahanFeedback(long id, String userEmail, int rating, String comment,String operation) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.rating = rating;
		this.comment = comment;
		this.operation = operation;
	}

	
	public ParivahanFeedback() {
		super();
	}

	
	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public long getId() {
		return id;
	}

	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public void setId(long id) {
		this.id = id;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
