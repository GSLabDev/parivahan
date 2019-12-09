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
package com.gslab.parivahan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.LatLng;
import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.util.DateUtil;
import com.gslab.parivahan.util.Vehicle;

import io.swagger.annotations.ApiModelProperty;

/**
 * Ride
 */
@Validated
@JsonInclude(Include.NON_NULL)
public class Ride   {
	 @JsonProperty("id")
    private String id = null;

	  @JsonProperty("startLocation")
	  private String startLocation = null;

	  @JsonProperty("endLocation")
	  private String endLocation = null;

	  @JsonProperty("departureDate")
	  private String departureDate = null;

	  @JsonProperty("departureHours")
	  private String departureHours = null;

	  @JsonProperty("totalSeats")
	  private Integer totalSeats = null;

	  @NotNull
	  @JsonProperty("availableSeats")
	  private Integer availableSeats = null;

	  @JsonProperty("startCoordinate")
	  private String startCoordinate = null;

	  @JsonProperty("endCoordinate")
	  private String endCoordinate = null;

	  @JsonProperty("charges")
	  private Integer charges = null;

	  @JsonProperty("stopOvers")
	  @Valid
	  private List<String> stopOvers = null;

	  @JsonProperty("totalRidesOffered")
	  private Integer totalRidesOffered = null;

	  @JsonProperty("ridePublishedDate")
	  private String ridePublishedDate = null;

	  @JsonProperty("ridePublishedTime")
	  private String ridePublishedTime = null;

	  @JsonProperty("encodedPath")
	  private String encodedPath = null;
	  
	  @JsonProperty("selectedRidePath")
	  private String selectedRidePath;

	  @JsonProperty("host")
	  private Host host = null;
	  
	  @JsonProperty("user")
	  private UserVO userVO;

	  @JsonProperty("vehicle")
	  private Vehicle vehicle = null;
	  
	  @JsonProperty("rideRequests")
	  private List<RideRequestVO> rideRequests;
	  


	public Ride(String startLocation, String endLocation, Integer totalSeats) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.totalSeats = totalSeats;
	}

	public Ride(com.gslab.parivahan.dbmodel.Ride ride) {
		this.setId(ride.getRideId());
		this.setAvailableSeats(ride.getAvailableSeats());
		this.setEncodedPath(ride.getEncodedPath());
		this.setEndCoordinate(ride.getEndCoordinate());
		this.setEndLocation(ride.getEndLocation());
		this.setStartCoordinate(ride.getStartCoordinate());
		this.setStartLocation(ride.getStartLocation());
		this.setTotalSeats(ride.getTotalSeats());
		if(null != ride.getDepartureDateTime()) {
			this.setDepartureDate(DateUtil.DATE_FORMATTER_DATE_AND_TIME.format(ride.getDepartureDateTime()));
		}
		if(ride.getUser()!=null) {
			this.setUserVO(
					new UserVO(ride.getUser().getEmail(), ride.getUser().getMobileNumber(), ride.getUser().getGender(),
							ride.getUser().getFirstName(), ride.getUser().getLastName(), ride.getUser().getUsername()));
		}
		if(!StringUtils.isBlank(ride.getVehicle()))
			this.setVehicle(Vehicle.valueOf(ride.getVehicle().toUpperCase()));
		else {
			this.setVehicle(null);
		}
		this.setCharges(ride.getCharges());
	}

	public Ride() {
		// TODO Auto-generated constructor stub
	}

	public Ride id(final String id) {
	    this.id = id;
	    return this;
	  }

	  /**
	   * Get id
	   * @return id
	  **/
	  @ApiModelProperty(example = "1200", value = "")


    public String getId() {
	    return id;
	  }

    public void setId(final String id) {
	    this.id = id;
	  }

	  public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getSelectedRidePath() {
		return selectedRidePath;
	}

	public void setSelectedRidePath(String selectedRidePath) {
		this.selectedRidePath = selectedRidePath;
	}

	public Ride startLocation(final String startLocation) {
	    this.startLocation = startLocation;
	    return this;
	  }

	  /**
	   * Get startLocation
	   * @return startLocation
	  **/
	  @ApiModelProperty(example = "Gslab pune", value = "")


	  public String getStartLocation() {
	    return startLocation;
	  }

	  public void setStartLocation(final String startLocation) {
	    this.startLocation = startLocation;
	  }

	  public Ride endLocation(final String endLocation) {
	    this.endLocation = endLocation;
	    return this;
	  }

	  /**
	   * Get endLocation
	   * @return endLocation
	  **/
	  @ApiModelProperty(example = "Pune University", value = "")


	  public String getEndLocation() {
	    return endLocation;
	  }

	  public void setEndLocation(final String endLocation) {
	    this.endLocation = endLocation;
	  }

	  public List<RideRequestVO> getRideRequests() {
		return rideRequests;
	}

	public void setRideRequests(List<RideRequestVO> rideRequestsVO) {
		this.rideRequests = rideRequestsVO;
	}

	public Ride departureDate(final String departureDate) {
	    this.departureDate = departureDate;
	    return this;
	  }

	  /**
	   * Get departureDate
	   * @return departureDate
	  **/
	  @ApiModelProperty(example = "2019-02-2T07:46:22:427Z", required = true, value = "")
	  @NotNull


	  public String getDepartureDate() {
	    return departureDate;
	  }

	  public void setDepartureDate(final String departureDate) {
	    this.departureDate = departureDate;
	  }

	  public Ride departureHours(final String departureHours) {
	    this.departureHours = departureHours;
	    return this;
	  }

	  /**
	   * Get departureHours
	   * @return departureHours
	  **/
	  @ApiModelProperty(example = "0000", required = true, value = "")


	  public String getDepartureHours() {
	    return departureHours;
	  }

	  public void setDepartureHours(final String departureHours) {
	    this.departureHours = departureHours;
	  }

	  public Ride totalSeats(final Integer totalSeats) {
	    this.totalSeats = totalSeats;
	    return this;
	  }

	  /**
	   * Get totalSeats
	   * @return totalSeats
	  **/
	  @ApiModelProperty(example = "4", value = "")


	  public Integer getTotalSeats() {
	    return totalSeats;
	  }

	  public void setTotalSeats(final Integer totalSeats) {
	    this.totalSeats = totalSeats;
	  }

	  public Ride availableSeats(final Integer availableSeats) {
	    this.availableSeats = availableSeats;
	    return this;
	  }

	  /**
	   * Get availableSeats
	   * @return availableSeats
	  **/
	  @ApiModelProperty(example = "3", required = true, value = "")
	  @NotNull


	  public Integer getAvailableSeats() {
	    return availableSeats;
	  }

	  public void setAvailableSeats(final Integer availableSeats) {
	    this.availableSeats = availableSeats;
	  }

	  public Ride startCoordinate(final String startCoordinate) {
	    this.startCoordinate = startCoordinate;
	    return this;
	  }

	  /**
	   * Get startCoordinate
	   * @return startCoordinate
	  **/
	  @ApiModelProperty(example = "18.5204,73.8567", required = true, value = "")


	  public String getStartCoordinate() {
	    return startCoordinate;
	  }

	  public void setStartCoordinate(final String startCoordinate) {
	    this.startCoordinate = startCoordinate;
	  }

	  public Ride endCoordinate(final String endCoordinate) {
	    this.endCoordinate = endCoordinate;
	    return this;
	  }

	  /**
	   * Get endCoordinate
	   * @return endCoordinate
	  **/
	  @ApiModelProperty(example = "19.2183,72.9781", required = true, value = "")


	  public String getEndCoordinate() {
	    return endCoordinate;
	  }

	  public void setEndCoordinate(final String endCoordinate) {
	    this.endCoordinate = endCoordinate;
	  }

	  public Ride charges(final Integer charges) {
	    this.charges = charges;
	    return this;
	  }

	  /**
	   * Get charges
	   * @return charges
	  **/
	  @ApiModelProperty(example = "100", required = true, value = "")


	  public Integer getCharges() {
	    return charges;
	  }

	  public void setCharges(final Integer charges) {
	    this.charges = charges;
	  }

	  public Ride stopOvers(final List<String> stopOvers) {
	    this.stopOvers = stopOvers;
	    return this;
	  }

	  public Ride addStopOversItem(final String stopOversItem) {
	    if (this.stopOvers == null) {
	      this.stopOvers = new ArrayList<String>();
	    }
	    this.stopOvers.add(stopOversItem);
	    return this;
	  }

	  /**
	   * Get stopOvers
	   * @return stopOvers
	  **/
	  @ApiModelProperty(value = "")


	  public List<String> getStopOvers() {
	    return stopOvers;
	  }

	  public void setStopOvers(final List<String> stopOvers) {
	    this.stopOvers = stopOvers;
	  }

	  public Ride totalRidesOffered(final Integer totalRidesOffered) {
	    this.totalRidesOffered = totalRidesOffered;
	    return this;
	  }

	  /**
	   * Get totalRidesOffered
	   * @return totalRidesOffered
	  **/
	  @ApiModelProperty(example = "18", value = "")


	  public Integer getTotalRidesOffered() {
	    return totalRidesOffered;
	  }

	  public void setTotalRidesOffered(final Integer totalRidesOffered) {
	    this.totalRidesOffered = totalRidesOffered;
	  }

	  public Ride ridePublishedDate(final String ridePublishedDate) {
	    this.ridePublishedDate = ridePublishedDate;
	    return this;
	  }

	  /**
	   * Get ridePublishedDate
	   * @return ridePublishedDate
	  **/
	  @ApiModelProperty(example = "15-08-2018", value = "")


	  public String getRidePublishedDate() {
	    return ridePublishedDate;
	  }

	  public void setRidePublishedDate(final String ridePublishedDate) {
	    this.ridePublishedDate = ridePublishedDate;
	  }

	  public Ride ridePublishedTime(final String ridePublishedTime) {
	    this.ridePublishedTime = ridePublishedTime;
	    return this;
	  }

	  /**
	   * Get ridePublishedTime
	   * @return ridePublishedTime
	  **/
	  @ApiModelProperty(example = "1600", value = "")


	  public String getRidePublishedTime() {
	    return ridePublishedTime;
	  }

	  public void setRidePublishedTime(final String ridePublishedTime) {
	    this.ridePublishedTime = ridePublishedTime;
	  }

	  public Ride encodedPath(final String encodedPath) {
	    this.encodedPath = encodedPath;
	    return this;
	  }

	  /**
	   * Get encodedPath
	   * @return encodedPath
	  **/
	  @ApiModelProperty(example = "bgcduh435ry78hjshc984yfhichkidf4yiuwbpxjdskcn", value = "")


	  public String getEncodedPath() {
	    return encodedPath;
	  }

	  public void setEncodedPath(final String encodedPath) {
	    this.encodedPath = encodedPath;
	  }

	  public Ride host(final Host host) {
	    this.host = host;
	    return this;
	  }

	  /**
	   * Get host
	   * @return host
	  **/
	  @ApiModelProperty(value = "")

	  @Valid

	  public Host getHost() {
	    return host;
	  }

	  public void setHost(final Host host) {
	    this.host = host;
	  }


	/**
	 * Get vehicle
	 * 
	 * @return vehicle
	 **/
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	  @Override
	  public boolean equals(final java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    final Ride Ride = (Ride) o;
	    return Objects.equals(this.id, Ride.id) &&
	        Objects.equals(this.startLocation, Ride.startLocation) &&
	        Objects.equals(this.endLocation, Ride.endLocation) &&
	        Objects.equals(this.departureDate, Ride.departureDate) &&
	        Objects.equals(this.departureHours, Ride.departureHours) &&
	        Objects.equals(this.totalSeats, Ride.totalSeats) &&
	        Objects.equals(this.availableSeats, Ride.availableSeats) &&
	        Objects.equals(this.startCoordinate, Ride.startCoordinate) &&
	        Objects.equals(this.endCoordinate, Ride.endCoordinate) &&
	        Objects.equals(this.charges, Ride.charges) &&
	        Objects.equals(this.stopOvers, Ride.stopOvers) &&
	        Objects.equals(this.totalRidesOffered, Ride.totalRidesOffered) &&
	        Objects.equals(this.ridePublishedDate, Ride.ridePublishedDate) &&
	        Objects.equals(this.ridePublishedTime, Ride.ridePublishedTime) &&
	        Objects.equals(this.encodedPath, Ride.encodedPath) &&
	        Objects.equals(this.host, Ride.host) &&
	        Objects.equals(this.vehicle, Ride.vehicle);
	  }

	

	@Override
	  public int hashCode() {
	    return Objects.hash(id, startLocation, endLocation, departureDate, departureHours, totalSeats, availableSeats, startCoordinate, endCoordinate, charges, stopOvers, totalRidesOffered, ridePublishedDate, ridePublishedTime, encodedPath, host, vehicle);
	  }

	  @Override
	  public String toString() {
	    final StringBuilder sb = new StringBuilder();
	    sb.append("class Ride {\n");

	    sb.append("    id: ").append(toIndentedString(id)).append("\n");
	    sb.append("    startLocation: ").append(toIndentedString(startLocation)).append("\n");
	    sb.append("    endLocation: ").append(toIndentedString(endLocation)).append("\n");
	    sb.append("    departureDate: ").append(toIndentedString(departureDate)).append("\n");
	    sb.append("    departureHours: ").append(toIndentedString(departureHours)).append("\n");
	    sb.append("    totalSeats: ").append(toIndentedString(totalSeats)).append("\n");
	    sb.append("    availableSeats: ").append(toIndentedString(availableSeats)).append("\n");
	    sb.append("    startCoordinate: ").append(toIndentedString(startCoordinate)).append("\n");
	    sb.append("    endCoordinate: ").append(toIndentedString(endCoordinate)).append("\n");
	    sb.append("    charges: ").append(toIndentedString(charges)).append("\n");
	    sb.append("    stopOvers: ").append(toIndentedString(stopOvers)).append("\n");
	    sb.append("    totalRidesOffered: ").append(toIndentedString(totalRidesOffered)).append("\n");
	    sb.append("    ridePublishedDate: ").append(toIndentedString(ridePublishedDate)).append("\n");
	    sb.append("    ridePublishedTime: ").append(toIndentedString(ridePublishedTime)).append("\n");
	    sb.append("    encodedPath: ").append(toIndentedString(encodedPath)).append("\n");
	    sb.append("    host: ").append(toIndentedString(host)).append("\n");
	    sb.append("    vehicle: ").append(toIndentedString(vehicle)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(final java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
public LatLng getStartLatLng() {
	return getLatLng(getStartCoordinate());
}

public void setStartLatLng(final LatLng startLatLng) {
	this.startCoordinate=startLatLng.toString();
}

public LatLng getEndLatLng() {
	return getLatLng(getEndCoordinate());
}

public void setEndLatLng(final LatLng endLatLng) {
	this.endCoordinate= endLatLng.toString();
	}

private LatLng getLatLng(final String coordinate){
	final String[] latLongs = coordinate.split(",");
	if(latLongs.length == 2) {
        return new LatLng(Double.parseDouble(latLongs[0]), Double.parseDouble(latLongs[1]));
    }
	return null;
}
}
