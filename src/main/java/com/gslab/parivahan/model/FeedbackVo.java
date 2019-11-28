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
