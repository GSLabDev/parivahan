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
