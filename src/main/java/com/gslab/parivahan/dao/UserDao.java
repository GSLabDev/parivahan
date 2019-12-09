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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.repository.UserRepository;

@Repository
public class UserDao implements IUserDao {
	@Autowired
	UserRepository repository;

	@Override
	public List<User> getAllUsers() {
		List<User> users=new ArrayList<User>();
		Iterable<User> allUsers = repository.findAll();
		for (User user : allUsers) {
			users.add(user);
		}
		return users;
		
	}

	@Override
	public User addUser(UserVO userVO) {
		User existingUser = getUserByEmail(userVO.getEmail());
		if(existingUser!=null) {
			throw new RuntimeException("Email already registered!");
		}
		return repository.save(new User(userVO.getEmail(), userVO.getMobileNumber(), userVO.getGender(), userVO.getFirstName(),
				userVO.getLastName(),userVO.getUsername()));
	}

	@Override
	public User getUserByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public User getUserByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public User getUserByUsername(String username) {
		return repository.findByUsername(username);
	}
}
