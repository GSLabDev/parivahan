package com.gslab.parivahan.dbmodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.validation.annotation.Validated;

import com.gslab.parivahan.util.DateUtil;
import com.gslab.parivahan.util.RideCodeGenerator;
import com.gslab.parivahan.util.Vehicle;

/**
 * Ride
 */
@Validated

@Entity
@Table(name = "ride")
public class Ride extends ParivahanAudingInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "offer_code")
	private Integer offerCode;

	@Column(name = "rideId")
	private String rideId;

	@Column(name = "startLocation")
	private String startLocation;

	@Column(name = "endLocation")
	private String endLocation;

	@Column(name = "totalSeats")
	private Integer totalSeats;

	@Column(name = "availableSeats")
	private Integer availableSeats;

	@Column(name = "startCoordinate")
	private String startCoordinate;

	@Column(name = "endCoordinate")
	private String endCoordinate;

	@Column(name = "encodedPath")
	private String encodedPath;

	@Column(name = "departureDateTime")
	private Date departureDateTime;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "charges")
	private Integer charges;

	@Column(name = "vehicle")
	private String vehicle;

	@Column(name = "isDeleted")
	private Boolean isDeleted;

	public Ride(com.gslab.parivahan.model.Ride userRide) {
		this.setRideId(RideCodeGenerator.generateRandomId());
		this.setAvailableSeats(userRide.getAvailableSeats());
		this.setEncodedPath(userRide.getEncodedPath());
		this.setEndCoordinate(userRide.getEndCoordinate());
		this.setEndLocation(userRide.getEndLocation());
		this.setOfferCode(RideCodeGenerator.generateRandomNumber());
		this.setStartCoordinate(userRide.getStartCoordinate());
		this.setStartLocation(userRide.getStartLocation());
		this.setTotalSeats(userRide.getTotalSeats());
		Date departureDate = DateUtil.parseDate(DateUtil.DATE_FORMATTER_DATE_AND_TIME, userRide.getDepartureDate());
		if (null != departureDate) {
			this.setDepartureDateTime(departureDate);
		}
		if (userRide.getUserVO() != null) {
			this.setUser(new User(userRide.getUserVO().getEmail(), userRide.getUserVO().getMobileNumber(),
					userRide.getUserVO().getGender(), userRide.getUserVO().getFirstName(),
					userRide.getUserVO().getLastName(),userRide.getUserVO().getUsername()));
		}
		this.setCharges(userRide.getCharges());
		this.setVehicle(userRide.getVehicle().toString());
		this.setCreatedDate(new Date());
		this.setUpdatedDate(new Date());
	}

	public Ride() {
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public Integer getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(Integer offerCode) {
		this.offerCode = offerCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEncodedPath() {
		return encodedPath;
	}

	public void setEncodedPath(String encodedPath) {
		this.encodedPath = encodedPath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getStartCoordinate() {
		return startCoordinate;
	}

	public void setStartCoordinate(String startCoordinate) {
		this.startCoordinate = startCoordinate;
	}

	public String getEndCoordinate() {
		return endCoordinate;
	}

	public void setEndCoordinate(String endCoordinate) {
		this.endCoordinate = endCoordinate;
	}

	public Date getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(Date departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public Integer getCharges() {
		return charges;
	}

	public void setCharges(Integer charges) {
		this.charges = charges;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

}