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

import java.util.List;

import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.UserVO;

public interface IRideRequestDao {

	public RideRequest addRideRequest(RideRequest rideRequest);
	
	public List<RideRequest> getAllRideRequest();
	
	public List<RideRequest> getAllRideRequestByOfferCode(Integer offerCode);
	
	public RideRequest getRideRequestByBookingCode(Integer bookingCode);
	
	public RideRequest updateRideRequestStatus(String rideStatus,String bookingId,RideRequest rideRequest);

	public void deleteRideRequest(RideRequest rideRequest);

	public List<RideRequest> getRideRequestByRideId(long id);
	
	public RideRequest getRideRequestByBookingId(String bookingId);

	public List<RideRequest> getRideRequestByUser(User user);
}
