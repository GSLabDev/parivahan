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
