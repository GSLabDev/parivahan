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
