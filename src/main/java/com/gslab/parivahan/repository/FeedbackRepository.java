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
