package com.gslab.parivahan.repository;

import org.springframework.data.repository.CrudRepository;

import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByEmail(String email);

	User findByName(String name);
	
	User findByUsername(String username);
}
