package com.gslab.parivahan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gslab.parivahan.dao.FeedbackDao;
import com.gslab.parivahan.dao.UserDao;
import com.gslab.parivahan.dbmodel.ParivahanFeedback;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.FeedbackVo;
import com.gslab.parivahan.util.EmailValidator;

@Service
public class FeedbackService implements IFeedbackService{

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public FeedbackVo postFeedback(FeedbackVo feedbackVo) {
		if (!EmailValidator.validateEmail(feedbackVo.getUserEmail())) {
			throw new RuntimeException("Email id is not valid");
		}
		feedbackVo.setUserEmail(feedbackVo.getUserEmail().trim().toLowerCase());
		User user = userDao.getUserByEmail(feedbackVo.getUserEmail());
		if(null == user) {
			throw new RuntimeException("User Does not exist");
		}
		ParivahanFeedback feedback = new ParivahanFeedback(feedbackVo);
		Date currentDate = new Date();
		feedback.setCreatedDate(currentDate);
		feedback.setUpdatedDate(currentDate);
		feedback=feedbackDao.postFeedback(feedback);
		return new FeedbackVo(feedback);
	}
	
	@Override
	public List<FeedbackVo> getFeedbackByEmailId(String emailId) {
		if (!EmailValidator.validateEmail(emailId)) {
			throw new RuntimeException("Email id is not valid");
		}
		emailId=emailId.trim().toLowerCase();
		User user = userDao.getUserByEmail(emailId);
		if(null == user) {
			throw new RuntimeException("User Does not exist");
		}
		return convertListToFeedbackVo(feedbackDao.getFeedbackByEmailId(emailId));
	}
	
	@Override
	public List<FeedbackVo> getFeedbackByRatings(int rating) {
		if(rating < 1 || rating >5)
			throw new RuntimeException("Rating is invalid");
		return convertListToFeedbackVo(feedbackDao.getFeedbackByRatings(rating));
	}
	
	@Override
	public List<FeedbackVo> getRecentFeedback() {
		return convertListToFeedbackVo(feedbackDao.getRecentFeedback());
	}
	
	@Override
	public List<FeedbackVo> getFeedbackSortedByRatings() {
		return convertListToFeedbackVo(feedbackDao.getFeedbackSortedByRatings());
	}
	
	private List<FeedbackVo> convertListToFeedbackVo(List<ParivahanFeedback> list){
		
		List<FeedbackVo> feedbacks = new ArrayList<FeedbackVo>();
		if(!list.isEmpty()) {
			for (ParivahanFeedback parivahanFeedback : list) {
				feedbacks.add(new FeedbackVo(parivahanFeedback));
			}
		}
		return feedbacks;
	}
}
