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
package com.gslab.parivahan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gslab.parivahan.dbmodel.ParivahanFeedback;

public interface FeedbackRepository extends CrudRepository<ParivahanFeedback, Long> {

	List<ParivahanFeedback> findByUserEmail(String Email);
	
	@Query(value="select f.* from feedback f order by created_date desc",nativeQuery=true)
	List<ParivahanFeedback> findAllOrderByCreatedDateAsc();
	
	
	List<ParivahanFeedback> findByRating(int rating);
	
	@Query(value="select r.* from feedback r order by rating desc",nativeQuery=true)
	List<ParivahanFeedback> findAllOrderByRating();
}
