package com.gslab.parivahan.service;

import java.util.List;

import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.UserVO;

public interface IRideRequestService {

	public RideRequestVO addRideRequest(RideRequestVO rideRequestVo);
	
	public List<RideRequest> getAllRideRequest() ;
	
	public List<RideRequest> getRideRequestByOfferCode(Integer offerCode);
	
	public RideRequestVO getRideRequestByBookingCode(Integer bookingCode);
	
	public RideRequestVO updateRideRequestStatus(String rideStatus,String bookingId, String vehicle);

	public void deleteRequest(RideRequest rideRequest);

	public List<RideRequestVO> getRideRequestByUser(String email, String vehicleType);

	public List<Ride> getOffersByUser(String email);

}
