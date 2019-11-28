package com.gslab.parivahan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.util.RideStatus;

@JsonInclude(Include.NON_NULL)
public class RideRequestVO {

	private String startLocation;

	private String endLocation;
	
	private String rideId;
	
	private String userEmail;
	
	private Integer rideCharges;
	
	private RideStatus status;
	
	private Ride ride;
	
	private UserVO user;
	
	private String bookingId;
	

	public RideRequestVO(RideRequest rideRequest) {
	this.setEndLocation(rideRequest.getEndLocation());
	this.setRide(rideRequest.getRide()!=null?new Ride(rideRequest.getRide()):null);
	this.setStartLocation(rideRequest.getStartLocation());
	this.setUser(new UserVO(rideRequest.getUser()));
	this.setStatus(rideRequest.getStatus());
	this.setRideCharges(rideRequest.getRideCharges());
	//TODO hide booking code
	this.setBookingId(rideRequest.getBookingId());;
	}
	
	public RideRequestVO() {
		// TODO Auto-generated constructor stub
	}


	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}
	

	public Integer getRideCharges() {
		return rideCharges;
	}

	public void setRideCharges(Integer rideCharges) {
		this.rideCharges = rideCharges;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public RideStatus getStatus() {
		return status;
	}

	public void setStatus(RideStatus status) {
		this.status = status;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String email) {
		this.userEmail = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endLocation == null) ? 0 : endLocation.hashCode());
		result = prime * result + ((rideId == null) ? 0 : rideId.hashCode());
		result = prime * result + ((startLocation == null) ? 0 : startLocation.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RideRequestVO other = (RideRequestVO) obj;
		if (endLocation == null) {
			if (other.endLocation != null)
				return false;
		} else if (!endLocation.equals(other.endLocation))
			return false;
		if (rideId == null) {
			if (other.rideId != null)
				return false;
		} else if (!rideId.equals(other.rideId))
			return false;
		if (startLocation == null) {
			if (other.startLocation != null)
				return false;
		} else if (!startLocation.equals(other.startLocation))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RideRequestVO [startLocation=" + startLocation + ", endLocation=" + endLocation + ", rideId=" + rideId
				+ ", userId=" + userEmail + "]";
	}
	
	
}
