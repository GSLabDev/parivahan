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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.repository.RideRepository;
import com.gslab.parivahan.repository.UserRepository;
import com.gslab.parivahan.util.DateUtil;

@Repository
public class RideDao implements IRideDao{
	
	@Autowired
	RideRepository rideRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Ride addUserRide(Ride rideDBObject) {
		User user = rideDBObject.getUser();
		Date currentDate = new Date();
		if(user!=null) {
			User existingUser = userRepository.findByEmail(user.getEmail());
			if(existingUser!=null) {
				rideDBObject.setUser(existingUser);
			}
		}
		rideDBObject.setIsDeleted(false);
		rideDBObject.setCreatedDate(currentDate);
		rideDBObject.setUpdatedDate(currentDate);
		rideDBObject= rideRepository.save(rideDBObject);
		return rideDBObject;
	}

	

	@Override
	public List<Ride> getAllRides() {
		List<Ride>rides=new ArrayList<>();
		for (Ride ride : rideRepository.findAll()) {
			rides.add(ride);
		};
		return rides;
	}
	
	@Override
	public Ride getRideByRideId(String rideId) {
		Ride ride = rideRepository.findByRideId(rideId);
		return ride;
		
	}
	
	@Override
	public Ride getRideByRideOfferCode(Integer offerCode) {
		Ride ride = rideRepository.findByOfferCode(offerCode);
		return ride;
	}
	
	/**
	 *  Passing departure data parameter twice owing to limitation of jpa/hibernate: named parameters 
	 *  cannot be used in a query multiple times, they have to be declared as separate parameter
	 *  https://stackoverflow.com/questions/16251491/using-hibernate-named-parameter-twice
	 */
	@Override
	public List<Ride> getRidesForGivenDate(Date departureDate, Date searchLimitDateWithoutTime, String userEmail) {
		return rideRepository.getRidesForGivenDate(departureDate, searchLimitDateWithoutTime, userEmail);
	}
	
	@Override
	public List<Ride> getRecentRides(String emailId) {
		return rideRepository.findRecentRideOrderByDepartureDate(new Date(),emailId);
	}



	@Override
	@Transactional
	public void deleteRideByOfferCode(Integer offerCode) {
		rideRepository.deleteByOfferCode(offerCode);
	}



	@Override
	public int countTotalRides() {
		return rideRepository.countTotalRides(new Date());
	}
	
	@Override
	public List<Ride> getShuttleByDate(Date date){
		//ate endDate = new Date(date.getYear(), date.getMonth(), date.getDate(), 23, 59, 59);
		/*Calendar inputDate=Calendar.getInstance();
		inputDate.setTime(date);*/
		Calendar endDateCalender = Calendar.getInstance();
		endDateCalender.setTime(date);
		endDateCalender.set(Calendar.HOUR, 23);
		endDateCalender.set(Calendar.MINUTE, 59);
		endDateCalender.set(Calendar.SECOND, 59);
		
		
		return rideRepository.getShuttleByDate(date,endDateCalender.getTime());
	}



	@Override
	public List<Ride> getRideByUser(User user) {
		List<Ride> ridesFromDB=rideRepository.getRideByUser(user);
		return ridesFromDB;
	}
}
