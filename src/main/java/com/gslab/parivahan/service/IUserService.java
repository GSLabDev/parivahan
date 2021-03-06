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
