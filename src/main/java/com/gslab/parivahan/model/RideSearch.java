package com.gslab.parivahan.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.LatLng;

import io.swagger.annotations.ApiModelProperty;

/**
 * RideSearch
 */
@Validated

public class RideSearch  {
	  @JsonProperty("originWalkingDistance")
	  private String originWalkingDistance = null;

	  @JsonProperty("destinationWalkingDistance")
	  private String destinationWalkingDistance = null;

	  @JsonProperty("originWalkingDistanceInMeters")
	  private Long originWalkingDistanceInMeters = null;

	  @JsonProperty("destinationWalkingDistanceInMeters")
	  private Long destinationWalkingDistanceInMeters = null;

	  @JsonProperty("totalRideDistance")
	  private String totalRideDistance = null;

	  @JsonProperty("totalChargesForRide")
	  private Integer totalChargesForRide = null;

	  @JsonProperty("riderOriginCoordinate")
	  private String riderOriginCoordinate = null;

	  @JsonProperty("riderJoiningPointCoordinate")
	  private LatLng riderJoiningPointCoordinate = null;

	  @JsonProperty("walkingEncodedPath")
	  private String walkingEncodedPath = null;

	  @JsonProperty("walkingDurationInSeconds")
	  private String walkingDurationInSeconds = null;
	  
	  @JsonProperty("totalTravelDurationInSeconds")
	  private String totalTravelDurationInSeconds = null;
	  
	  @JsonProperty("riderEndPointCoordinate")
	  private LatLng riderEndPointCoordinate = null;

	  @JsonProperty("ride")
	  private Ride ride = null;

	  public RideSearch originWalkingDistance(String originWalkingDistance) {
	    this.originWalkingDistance = originWalkingDistance;
	    return this;
	  }
	  
	public RideSearch(final Ride Ride) {
		setRide(Ride);
		ride.setEncodedPath(Ride.getEncodedPath());
		ride.setEndLatLng(Ride.getEndLatLng());
		ride.setEndLocation(Ride.getEndLocation());
		ride.setStartLatLng(Ride.getStartLatLng());
		ride.setStartLocation(Ride.getStartLocation());
	}

	 /**
	   * Get originWalkingDistance
	   * @return originWalkingDistance
	  **/
	  @ApiModelProperty(example = "1.5km", value = "")


	  public String getOriginWalkingDistance() {
	    return originWalkingDistance;
	  }

	  public void setOriginWalkingDistance(String originWalkingDistance) {
	    this.originWalkingDistance = originWalkingDistance;
	  }

	  public RideSearch destinationWalkingDistance(String destinationWalkingDistance) {
	    this.destinationWalkingDistance = destinationWalkingDistance;
	    return this;
	  }

	  /**
	   * Get destinationWalkingDistance
	   * @return destinationWalkingDistance
	  **/
	  @ApiModelProperty(example = "1.5km", value = "")


	  public String getDestinationWalkingDistance() {
	    return destinationWalkingDistance;
	  }

	  public void setDestinationWalkingDistance(String destinationWalkingDistance) {
	    this.destinationWalkingDistance = destinationWalkingDistance;
	  }

	  public RideSearch originWalkingDistanceInMeters(Long originWalkingDistanceInMeters) {
	    this.originWalkingDistanceInMeters = originWalkingDistanceInMeters;
	    return this;
	  }

	  /**
	   * Get originWalkingDistanceInMeters
	   * @return originWalkingDistanceInMeters
	  **/
	  @ApiModelProperty(example = "1500", value = "")


	  public Long getOriginWalkingDistanceInMeters() {
	    return originWalkingDistanceInMeters;
	  }

	  public void setOriginWalkingDistanceInMeters(Long originWalkingDistanceInMeters) {
	    this.originWalkingDistanceInMeters = originWalkingDistanceInMeters;
	  }

	  public RideSearch destinationWalkingDistanceInMeters(Long destinationWalkingDistanceInMeters) {
	    this.destinationWalkingDistanceInMeters = destinationWalkingDistanceInMeters;
	    return this;
	  }

	  /**
	   * Get destinationWalkingDistanceInMeters
	   * @return destinationWalkingDistanceInMeters
	  **/
	  @ApiModelProperty(example = "1500", value = "")


	  public Long getDestinationWalkingDistanceInMeters() {
	    return destinationWalkingDistanceInMeters;
	  }

	  public void setDestinationWalkingDistanceInMeters(Long destinationWalkingDistanceInMeters) {
	    this.destinationWalkingDistanceInMeters = destinationWalkingDistanceInMeters;
	  }

	  public RideSearch totalRideDistance(String totalRideDistance) {
	    this.totalRideDistance = totalRideDistance;
	    return this;
	  }

	  /**
	   * Get totalRideDistance
	   * @return totalRideDistance
	  **/
	  @ApiModelProperty(example = "5000", value = "")


	  public String getTotalRideDistance() {
	    return totalRideDistance;
	  }

	  public void setTotalRideDistance(String humanReadable) {
	    this.totalRideDistance = humanReadable;
	  }

	  public RideSearch totalChargesForRide(Integer totalChargesForRide) {
	    this.totalChargesForRide = totalChargesForRide;
	    return this;
	  }

	  /**
	   * Get totalChargesForRide
	   * @return totalChargesForRide
	  **/
	  @ApiModelProperty(example = "50", value = "")


	  public Integer getTotalChargesForRide() {
	    return totalChargesForRide;
	  }

	  public void setTotalChargesForRide(Integer totalChargesForRide) {
	    this.totalChargesForRide = totalChargesForRide;
	  }

	  public RideSearch riderOriginCoordinate(String riderOriginCoordinate) {
	    this.riderOriginCoordinate = riderOriginCoordinate;
	    return this;
	  }

	  /**
	   * Get riderOriginCoordinate
	   * @return riderOriginCoordinate
	  **/
	  @ApiModelProperty(example = "18.554230,73.809636", value = "")


	  public String getRiderOriginCoordinate() {
	    return riderOriginCoordinate;
	  }

	  public void setRiderOriginCoordinate(String riderOriginCoordinate) {
	    this.riderOriginCoordinate = riderOriginCoordinate;
	  }

	  public RideSearch riderJoiningPointCoordinate(LatLng riderJoiningPointCoordinate) {
	    this.riderJoiningPointCoordinate = riderJoiningPointCoordinate;
	    return this;
	  }

	  /**
	   * Get riderJoiningPointCoordinate
	   * @return riderJoiningPointCoordinate
	  **/
	  @ApiModelProperty(example = "18.550069,73.809797", value = "")


	  public LatLng getRiderJoiningPointCoordinate() {
	    return riderJoiningPointCoordinate;
	  }

	  public void setRiderJoiningPointCoordinate(LatLng riderJoiningPointCoordinate) {
	    this.riderJoiningPointCoordinate = riderJoiningPointCoordinate;
	  }

	  public RideSearch walkingEncodedPath(String walkingEncodedPath) {
	    this.walkingEncodedPath = walkingEncodedPath;
	    return this;
	  }

	  /**
	   * Get walkingEncodedPath
	   * @return walkingEncodedPath
	  **/
	  @ApiModelProperty(example = "fywefg469ncjd@3mld@xekjd2w3$5%vfsd", value = "")


	  public String getWalkingEncodedPath() {
	    return walkingEncodedPath;
	  }

	  public void setWalkingEncodedPath(String walkingEncodedPath) {
	    this.walkingEncodedPath = walkingEncodedPath;
	  }

	  public RideSearch walkingDurationInSeconds(String walkingDurationInSeconds) {
	    this.walkingDurationInSeconds = walkingDurationInSeconds;
	    return this;
	  }

	  /**
	   * Get walkingDurationInSeconds
	   * @return walkingDurationInSeconds
	  **/
	  @ApiModelProperty(example = "720", value = "")


	  public String getWalkingDurationInSeconds() {
	    return walkingDurationInSeconds;
	  }

	  public void setWalkingDurationInSeconds(String walkingDurationInSeconds) {
	    this.walkingDurationInSeconds = walkingDurationInSeconds;
	  }
	  
	  /**
	   * Get totalTravelDurationInSeconds
	   * @return totalTravelDurationInSeconds
	  **/
	  @ApiModelProperty(example = "2000", value = "")


	  public String getTotalTravelDurationInSeconds() {
	    return totalTravelDurationInSeconds;
	  }

	  public void setTotalTravelDurationInSeconds(String totalTravelDurationInSeconds) {
	    this.totalTravelDurationInSeconds = totalTravelDurationInSeconds;
	  }

	  public RideSearch ride(Ride ride) {
	    this.ride = ride;
	    return this;
	  }

	  /**
	   * Get ride
	   * @return ride
	  **/
	  @ApiModelProperty(value = "")

	  @Valid

	  public Ride getRide() {
	    return ride;
	  }

	  public void setRide(Ride ride) {
	    this.ride = ride;
	  }

	  public LatLng getRiderEndPointCoordinate() {
		return riderEndPointCoordinate;
	}

	public void setRiderEndPointCoordinate(LatLng riderEndPointCoordinate) {
		this.riderEndPointCoordinate = riderEndPointCoordinate;
	}

	

	  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationWalkingDistance == null) ? 0 : destinationWalkingDistance.hashCode());
		result = prime * result
				+ ((destinationWalkingDistanceInMeters == null) ? 0 : destinationWalkingDistanceInMeters.hashCode());
		result = prime * result + ((originWalkingDistance == null) ? 0 : originWalkingDistance.hashCode());
		result = prime * result
				+ ((originWalkingDistanceInMeters == null) ? 0 : originWalkingDistanceInMeters.hashCode());
		result = prime * result + ((ride == null) ? 0 : ride.hashCode());
		result = prime * result + ((riderEndPointCoordinate == null) ? 0 : riderEndPointCoordinate.hashCode());
		result = prime * result + ((riderJoiningPointCoordinate == null) ? 0 : riderJoiningPointCoordinate.hashCode());
		result = prime * result + ((riderOriginCoordinate == null) ? 0 : riderOriginCoordinate.hashCode());
		result = prime * result + ((totalChargesForRide == null) ? 0 : totalChargesForRide.hashCode());
		result = prime * result + ((totalRideDistance == null) ? 0 : totalRideDistance.hashCode());
		result = prime * result
				+ ((totalTravelDurationInSeconds == null) ? 0 : totalTravelDurationInSeconds.hashCode());
		result = prime * result + ((walkingDurationInSeconds == null) ? 0 : walkingDurationInSeconds.hashCode());
		result = prime * result + ((walkingEncodedPath == null) ? 0 : walkingEncodedPath.hashCode());
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
		RideSearch other = (RideSearch) obj;
		if (destinationWalkingDistance == null) {
			if (other.destinationWalkingDistance != null)
				return false;
		} else if (!destinationWalkingDistance.equals(other.destinationWalkingDistance))
			return false;
		if (destinationWalkingDistanceInMeters == null) {
			if (other.destinationWalkingDistanceInMeters != null)
				return false;
		} else if (!destinationWalkingDistanceInMeters.equals(other.destinationWalkingDistanceInMeters))
			return false;
		if (originWalkingDistance == null) {
			if (other.originWalkingDistance != null)
				return false;
		} else if (!originWalkingDistance.equals(other.originWalkingDistance))
			return false;
		if (originWalkingDistanceInMeters == null) {
			if (other.originWalkingDistanceInMeters != null)
				return false;
		} else if (!originWalkingDistanceInMeters.equals(other.originWalkingDistanceInMeters))
			return false;
		if (ride == null) {
			if (other.ride != null)
				return false;
		} else if (!ride.equals(other.ride))
			return false;
		if (riderEndPointCoordinate == null) {
			if (other.riderEndPointCoordinate != null)
				return false;
		} else if (!riderEndPointCoordinate.equals(other.riderEndPointCoordinate))
			return false;
		if (riderJoiningPointCoordinate == null) {
			if (other.riderJoiningPointCoordinate != null)
				return false;
		} else if (!riderJoiningPointCoordinate.equals(other.riderJoiningPointCoordinate))
			return false;
		if (riderOriginCoordinate == null) {
			if (other.riderOriginCoordinate != null)
				return false;
		} else if (!riderOriginCoordinate.equals(other.riderOriginCoordinate))
			return false;
		if (totalChargesForRide == null) {
			if (other.totalChargesForRide != null)
				return false;
		} else if (!totalChargesForRide.equals(other.totalChargesForRide))
			return false;
		if (totalRideDistance == null) {
			if (other.totalRideDistance != null)
				return false;
		} else if (!totalRideDistance.equals(other.totalRideDistance))
			return false;
		if (totalTravelDurationInSeconds == null) {
			if (other.totalTravelDurationInSeconds != null)
				return false;
		} else if (!totalTravelDurationInSeconds.equals(other.totalTravelDurationInSeconds))
			return false;
		if (walkingDurationInSeconds == null) {
			if (other.walkingDurationInSeconds != null)
				return false;
		} else if (!walkingDurationInSeconds.equals(other.walkingDurationInSeconds))
			return false;
		if (walkingEncodedPath == null) {
			if (other.walkingEncodedPath != null)
				return false;
		} else if (!walkingEncodedPath.equals(other.walkingEncodedPath))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RideSearch [originWalkingDistance=");
		builder.append(originWalkingDistance);
		builder.append(", destinationWalkingDistance=");
		builder.append(destinationWalkingDistance);
		builder.append(", originWalkingDistanceInMeters=");
		builder.append(originWalkingDistanceInMeters);
		builder.append(", destinationWalkingDistanceInMeters=");
		builder.append(destinationWalkingDistanceInMeters);
		builder.append(", totalRideDistance=");
		builder.append(totalRideDistance);
		builder.append(", totalChargesForRide=");
		builder.append(totalChargesForRide);
		builder.append(", riderOriginCoordinate=");
		builder.append(riderOriginCoordinate);
		builder.append(", riderJoiningPointCoordinate=");
		builder.append(riderJoiningPointCoordinate);
		builder.append(", walkingEncodedPath=");
		builder.append(walkingEncodedPath);
		builder.append(", walkingDurationInSeconds=");
		builder.append(walkingDurationInSeconds);
		builder.append(", totalTravelDurationInSeconds=");
		builder.append(totalTravelDurationInSeconds);
		builder.append(", riderEndPointCoordinate=");
		builder.append(riderEndPointCoordinate);
		builder.append(", ride=");
		builder.append(ride);
		builder.append("]");
		return builder.toString();
	}

	/**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
}
