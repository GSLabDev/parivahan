package com.gslab.parivahan.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gslab.parivahan.dbmodel.ParivahanFeedback;
import com.gslab.parivahan.repository.FeedbackRepository;

@Repository
public class FeedbackDao implements IFeedbackDao{

	@Autowired
	private FeedbackRepository feedbackRepo;
	
	@Override
	public ParivahanFeedback postFeedback(ParivahanFeedback feedback) {
		return feedbackRepo.save(feedback);
	}
	
	@Override
	public List<ParivahanFeedback> getFeedbackByEmailId(String emailId) {
		return feedbackRepo.findByUserEmail(emailId);
	}
	
	@Override
	public List<ParivahanFeedback> getFeedbackByRatings(int rating) {
		return feedbackRepo.findByRating(rating);
	}
	
	
	@Override
	public List<ParivahanFeedback> getRecentFeedback() {
		return feedbackRepo.findAllOrderByCreatedDateAsc();
	}

	@Override
	public List<ParivahanFeedback> getFeedbackSortedByRatings() {
		return feedbackRepo.findAllOrderByRating();
	}
	 
	
	

}
