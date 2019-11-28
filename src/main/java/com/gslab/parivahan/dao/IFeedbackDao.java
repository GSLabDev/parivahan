package com.gslab.parivahan.dao;

import java.util.List;

import com.gslab.parivahan.dbmodel.ParivahanFeedback;

public interface IFeedbackDao {

	public ParivahanFeedback postFeedback(ParivahanFeedback feedback);

	List<ParivahanFeedback> getFeedbackByEmailId(String emailId);

	List<ParivahanFeedback> getFeedbackByRatings(int rating);

	List<ParivahanFeedback> getRecentFeedback();

	List<ParivahanFeedback> getFeedbackSortedByRatings();
}
