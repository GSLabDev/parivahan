package com.gslab.parivahan.dao;

import java.util.List;

import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.UserVO;

public interface IUserDao {
	public List<User> getAllUsers();

	public User addUser(UserVO userVO);

	public User getUserByEmail(String email);

	public User getUserByName(String name);

	public User getUserByUsername(String username);
}
