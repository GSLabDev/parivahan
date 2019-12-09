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

import java.util.Date;
import java.util.List;

import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.User;

public interface IRideDao{

	Ride addUserRide(Ride rideDBObject);

	List<Ride> getAllRides();

	List<Ride> getRidesForGivenDate(Date departureDate,Date searchLimitDateWithoutTime, String userEmail);
	
	Ride getRideByRideId(String rideId);

	void deleteRideByOfferCode(Integer offerCode);

	com.gslab.parivahan.dbmodel.Ride getRideByRideOfferCode(Integer offerCode);
	
	List<Ride> getRecentRides(String emailId);
	
	int	countTotalRides();
	
	public List<Ride> getShuttleByDate(Date date);

	List<Ride> getRideByUser(User user);
}
