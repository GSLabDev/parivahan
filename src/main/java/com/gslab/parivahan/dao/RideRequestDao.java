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
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.repository.RideRepository;
import com.gslab.parivahan.repository.RideRequestRepository;
import com.gslab.parivahan.util.RideStatus;

@Repository
public class RideRequestDao implements IRideRequestDao{

	@Autowired
	private RideRequestRepository rideRequestRepository;
	
	@Autowired
	private RideRepository rideRepository;
	
	public RideRequest addRideRequest(RideRequest rideRequest) {
		Date currentDate = new Date();
		rideRequest.setCreatedDate(currentDate);
		rideRequest.setUpdatedDate(currentDate);
		rideRequest = rideRequestRepository.save(rideRequest);
		return rideRequest;
	}
	
	public List<RideRequest> getAllRideRequest() {
		List<RideRequest> ridesReq = new ArrayList<RideRequest>();
		Iterable<RideRequest> rideRequests = rideRequestRepository.findAll();
		for (RideRequest rideRequest : rideRequests) {
			ridesReq.add(rideRequest);
		}
		return ridesReq;
	}
	
	public List<RideRequest> getAllRideRequestByOfferCode(Integer OfferCode){
		return rideRequestRepository.getAllRidesRequestsByOfferCode(OfferCode);
	}
	
	@Override
	public List<RideRequest> getRideRequestByRideId(long id) {
		return rideRequestRepository.getRideRequestByRideId(id);
	}
	
	public RideRequest getRideRequestByBookingCode(Integer bookingCode){
		return rideRequestRepository.findByBookingCode(bookingCode);
	}
	//Add transaction
	public RideRequest updateRideRequestStatus(String rideStatus,String bookingId,RideRequest rideRequest) {
		Ride ride = rideRepository.findRideByBookingId(bookingId);
		Date currentDate = new Date();
		if(rideStatus.equalsIgnoreCase(RideStatus.ACCEPTED.toString())) {
			if (ride.getAvailableSeats() > 0) {
				rideRequest.setStatus(RideStatus.valueOf(rideStatus));
				rideRequest = rideRequestRepository.save(rideRequest);
				ride.setAvailableSeats(ride.getAvailableSeats()-1);
				rideRequest.setUpdatedDate(currentDate);
				rideRepository.save(ride);
			}else {
				throw new RuntimeException("No seats available for the requested ride");
			}
		}else if(rideStatus.equalsIgnoreCase(RideStatus.CANCELLED.toString())) {
			if(RideStatus.ACCEPTED.toString().equalsIgnoreCase(rideRequest.getStatus().toString())) {
				if (ride.getAvailableSeats() >= 0) {
					rideRequest.setStatus(RideStatus.valueOf(rideStatus));
					rideRequest = rideRequestRepository.save(rideRequest);
					ride.setAvailableSeats(ride.getAvailableSeats()+1);
					rideRequest.setUpdatedDate(currentDate);
					rideRepository.save(ride);
				}else {
					throw new RuntimeException("No seats available for the requested ride");
				}
			}else if(RideStatus.PENDING.toString().equalsIgnoreCase(rideRequest.getStatus().toString())) {
				rideRequest.setStatus(RideStatus.valueOf(rideStatus));
				rideRequest.setUpdatedDate(currentDate);
				rideRequest = rideRequestRepository.save(rideRequest);
			}
			
		}else {
			rideRequest.setUpdatedDate(currentDate);
			rideRequest.setStatus(RideStatus.valueOf(rideStatus));
			rideRequest = rideRequestRepository.save(rideRequest);
		}
		
		return rideRequest;
	}

	@Override
	@Transactional
	public void deleteRideRequest(RideRequest rideRequest) {
		rideRequestRepository.deleteRideRequest(rideRequest.getId());
	}
	
	
	public RideRequest getRideRequestByBookingId(String bookingId){
		return rideRequestRepository.findByBookingId(bookingId);
	}

	@Override
	public List<RideRequest> getRideRequestByUser(User user) {
		List<RideRequest> rideRequests= rideRequestRepository.findRequestsByUser(user);
		return rideRequests;
	}


}
