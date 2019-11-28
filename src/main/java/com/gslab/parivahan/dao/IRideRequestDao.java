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
