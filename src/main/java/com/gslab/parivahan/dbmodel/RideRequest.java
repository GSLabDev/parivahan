package com.gslab.parivahan.dbmodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gslab.parivahan.util.RideCodeGenerator;
import com.gslab.parivahan.util.RideStatus;

@Entity
@Table(name="ride_request")
@JsonInclude(Include.NON_NULL)
public class RideRequest extends ParivahanAudingInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(name = "start_Location")
	private String startLocation;

	@Column(name = "end_Location")
	private String endLocation;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
    @JoinColumn(name = "ride_id")
	private Ride ride;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="status")
	private RideStatus status;
	
	@Column(name="booking_code")
	private Integer bookingCode;
	
	@Column(name="ride_charges")
	private Integer rideCharges;

	@Column(name="is_deleted")
	private Boolean isDeleted;
	
	@Column(name="booking_id")
	private String bookingId;
	
	public RideRequest() {
		super();
	}


	public RideRequest(String startLocation, String endLocation, Ride rideId, User user, RideStatus status,
			Integer bookingCode) {
		super(new Date(),new Date());
		this.bookingId = RideCodeGenerator.generateRandomId();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.ride = rideId;
		this.user = user;
		this.status = status;
		this.bookingCode = bookingCode;
	}


	public Integer getRideCharges() {
		return rideCharges;
	}


	public void setRideCharges(Integer price) {
		this.rideCharges = price;
	}


	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public Ride getRide() {
		return ride;
	}

	public void setRideId(Ride ride) {
		this.ride = ride;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RideStatus getStatus() {
		return status;
	}

	public void setStatus(RideStatus status) {
		this.status = status;
	}

	public Integer getBookingCode() {
		return bookingCode;
	}


	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}


	public void setRide(Ride ride) {
		this.ride = ride;
	}
	
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + ((bookingCode == null) ? 0 : bookingCode.hashCode());
		result = prime * result + ((bookingId == null) ? 0 : bookingId.hashCode());
		result = prime * result + ((endLocation == null) ? 0 : endLocation.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((ride == null) ? 0 : ride.hashCode());
		result = prime * result + ((rideCharges == null) ? 0 : rideCharges.hashCode());
		result = prime * result + ((startLocation == null) ? 0 : startLocation.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		RideRequest other = (RideRequest) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (bookingCode == null) {
			if (other.bookingCode != null)
				return false;
		} else if (!bookingCode.equals(other.bookingCode))
			return false;
		if (bookingId == null) {
			if (other.bookingId != null)
				return false;
		} else if (!bookingId.equals(other.bookingId))
			return false;
		if (endLocation == null) {
			if (other.endLocation != null)
				return false;
		} else if (!endLocation.equals(other.endLocation))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (ride == null) {
			if (other.ride != null)
				return false;
		} else if (!ride.equals(other.ride))
			return false;
		if (rideCharges == null) {
			if (other.rideCharges != null)
				return false;
		} else if (!rideCharges.equals(other.rideCharges))
			return false;
		if (startLocation == null) {
			if (other.startLocation != null)
				return false;
		} else if (!startLocation.equals(other.startLocation))
			return false;
		if (status != other.status)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RideRequest [Id=" + Id + ", startLocation=" + startLocation + ", endLocation=" + endLocation
				+ ", status=" + status + ", bookingCode=" + bookingCode + ", rideCharges=" + rideCharges
				+ ", isDeleted=" + isDeleted + ", bookingId=" + bookingId + "]";
	}
}
