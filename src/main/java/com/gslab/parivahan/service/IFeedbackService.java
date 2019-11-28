package com.gslab.parivahan.service;

import java.util.List;

import com.gslab.parivahan.model.FeedbackVo;

public interface IFeedbackService {

	public FeedbackVo postFeedback(FeedbackVo feedbackVo);

	List<FeedbackVo> getFeedbackSortedByRatings();

	List<FeedbackVo> getRecentFeedback();

	List<FeedbackVo> getFeedbackByRatings(int rating);

	List<FeedbackVo> getFeedbackByEmailId(String emailId);
}
