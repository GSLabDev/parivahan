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
