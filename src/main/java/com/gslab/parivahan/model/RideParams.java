package com.gslab.parivahan.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * RideParams
 */
@Validated

public class RideParams   {
	@JsonProperty("riderDestinationCoordinate")
	  private String riderDestinationCoordinate = null;

	  @JsonProperty("riderJoiningPointCoordinate")
	  private String riderJoiningPointCoordinate = null;

	  @JsonProperty("totalTravelDurationInSeconds")
	  private String totalTravelDurationInSeconds = null;

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

	  public RideParams riderDestinationCoordinate(String riderDestinationCoordinate) {
	    this.riderDestinationCoordinate = riderDestinationCoordinate;
	    return this;
	  }

	  /**
	   * Get riderDestinationCoordinate
	   * @return riderDestinationCoordinate
	  **/
	  @ApiModelProperty(example = "18.554230,73.809636", value = "")


	  public String getRiderDestinationCoordinate() {
	    return riderDestinationCoordinate;
	  }

	  public void setRiderDestinationCoordinate(String riderDestinationCoordinate) {
	    this.riderDestinationCoordinate = riderDestinationCoordinate;
	  }

	  public RideParams riderJoiningPointCoordinate(String riderJoiningPointCoordinate) {
	    this.riderJoiningPointCoordinate = riderJoiningPointCoordinate;
	    return this;
	  }

	  /**
	   * Get riderJoiningPointCoordinate
	   * @return riderJoiningPointCoordinate
	  **/
	  @ApiModelProperty(example = "18.550069,73.809797", value = "")


	  public String getRiderJoiningPointCoordinate() {
	    return riderJoiningPointCoordinate;
	  }

	  public void setRiderJoiningPointCoordinate(String riderJoiningPointCoordinate) {
	    this.riderJoiningPointCoordinate = riderJoiningPointCoordinate;
	  }

	  public RideParams totalTravelDurationInSeconds(String totalTravelDurationInSeconds) {
	    this.totalTravelDurationInSeconds = totalTravelDurationInSeconds;
	    return this;
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

	  public RideParams originWalkingDistance(String originWalkingDistance) {
	    this.originWalkingDistance = originWalkingDistance;
	    return this;
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

	  public RideParams destinationWalkingDistance(String destinationWalkingDistance) {
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

	  public RideParams originWalkingDistanceInMeters(Long originWalkingDistanceInMeters) {
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

	  public RideParams destinationWalkingDistanceInMeters(Long destinationWalkingDistanceInMeters) {
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

	  public RideParams totalRideDistance(String totalRideDistance) {
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

	  public void setTotalRideDistance(String totalRideDistance) {
	    this.totalRideDistance = totalRideDistance;
	  }

	  public RideParams totalChargesForRide(Integer totalChargesForRide) {
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


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    RideParams rideParams = (RideParams) o;
	    return Objects.equals(this.riderDestinationCoordinate, rideParams.riderDestinationCoordinate) &&
	        Objects.equals(this.riderJoiningPointCoordinate, rideParams.riderJoiningPointCoordinate) &&
	        Objects.equals(this.totalTravelDurationInSeconds, rideParams.totalTravelDurationInSeconds) &&
	        Objects.equals(this.originWalkingDistance, rideParams.originWalkingDistance) &&
	        Objects.equals(this.destinationWalkingDistance, rideParams.destinationWalkingDistance) &&
	        Objects.equals(this.originWalkingDistanceInMeters, rideParams.originWalkingDistanceInMeters) &&
	        Objects.equals(this.destinationWalkingDistanceInMeters, rideParams.destinationWalkingDistanceInMeters) &&
	        Objects.equals(this.totalRideDistance, rideParams.totalRideDistance) &&
	        Objects.equals(this.totalChargesForRide, rideParams.totalChargesForRide);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(riderDestinationCoordinate, riderJoiningPointCoordinate, totalTravelDurationInSeconds, originWalkingDistance, destinationWalkingDistance, originWalkingDistanceInMeters, destinationWalkingDistanceInMeters, totalRideDistance, totalChargesForRide);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class RideParams {\n");
	    
	    sb.append("    riderDestinationCoordinate: ").append(toIndentedString(riderDestinationCoordinate)).append("\n");
	    sb.append("    riderJoiningPointCoordinate: ").append(toIndentedString(riderJoiningPointCoordinate)).append("\n");
	    sb.append("    totalTravelDurationInSeconds: ").append(toIndentedString(totalTravelDurationInSeconds)).append("\n");
	    sb.append("    originWalkingDistance: ").append(toIndentedString(originWalkingDistance)).append("\n");
	    sb.append("    destinationWalkingDistance: ").append(toIndentedString(destinationWalkingDistance)).append("\n");
	    sb.append("    originWalkingDistanceInMeters: ").append(toIndentedString(originWalkingDistanceInMeters)).append("\n");
	    sb.append("    destinationWalkingDistanceInMeters: ").append(toIndentedString(destinationWalkingDistanceInMeters)).append("\n");
	    sb.append("    totalRideDistance: ").append(toIndentedString(totalRideDistance)).append("\n");
	    sb.append("    totalChargesForRide: ").append(toIndentedString(totalChargesForRide)).append("\n");
	    sb.append("}");
	    return sb.toString();
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

