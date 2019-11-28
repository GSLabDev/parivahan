package com.gslab.parivahan.service;

import java.util.List;

import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.UserVO;

public interface IUserService {
	public List<UserVO> getAllUsers();

	public UserVO getUserByEmail(String email);
	public UserVO addUser(UserVO userVO);
	public UserVO getUserByUsername(String username);
	
	public List<RideRequestVO> getBookingOfUser(String email, String type);
	public List<Ride> getOffersOfUser(String email);
	public UserVO getUserByName(String name);

}
