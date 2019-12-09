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

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Host
 */
@Validated

public class Host   {
  @JsonProperty("hostName")
  private String hostName = null;

  @JsonProperty("hostAge")
  private Integer hostAge = null;

  @JsonProperty("hostGender")
  private String hostGender = null;

  @JsonProperty("isPhoneNumberVerified")
  private Boolean isPhoneNumberVerified = null;

  @JsonProperty("isGovtIdverified")
  private Boolean isGovtIdverified = null;

  @JsonProperty("Rating")
  private String rating = null;

  @JsonProperty("totalHostRatings")
  private Integer totalHostRatings = null;

  public Host hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

  /**
   * Get hostName
   * @return hostName
  **/
  @ApiModelProperty(example = "Swapnil", value = "")


  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public Host hostAge(Integer hostAge) {
    this.hostAge = hostAge;
    return this;
  }

  /**
   * Get hostAge
   * @return hostAge
  **/
  @ApiModelProperty(example = "26", value = "")


  public Integer getHostAge() {
    return hostAge;
  }

  public void setHostAge(Integer hostAge) {
    this.hostAge = hostAge;
  }

  public Host hostGender(String hostGender) {
    this.hostGender = hostGender;
    return this;
  }

  /**
   * Get hostGender
   * @return hostGender
  **/
  @ApiModelProperty(example = "Male", value = "")


  public String getHostGender() {
    return hostGender;
  }

  public void setHostGender(String hostGender) {
    this.hostGender = hostGender;
  }

  public Host isPhoneNumberVerified(Boolean isPhoneNumberVerified) {
    this.isPhoneNumberVerified = isPhoneNumberVerified;
    return this;
  }

  /**
   * Get isPhoneNumberVerified
   * @return isPhoneNumberVerified
  **/
  @ApiModelProperty(example = "true", value = "")


  public Boolean isIsPhoneNumberVerified() {
    return isPhoneNumberVerified;
  }

  public void setIsPhoneNumberVerified(Boolean isPhoneNumberVerified) {
    this.isPhoneNumberVerified = isPhoneNumberVerified;
  }

  public Host isGovtIdverified(Boolean isGovtIdverified) {
    this.isGovtIdverified = isGovtIdverified;
    return this;
  }

  /**
   * Get isGovtIdverified
   * @return isGovtIdverified
  **/
  @ApiModelProperty(example = "false", value = "")


  public Boolean isIsGovtIdverified() {
    return isGovtIdverified;
  }

  public void setIsGovtIdverified(Boolean isGovtIdverified) {
    this.isGovtIdverified = isGovtIdverified;
  }

  public Host rating(String rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
  **/
  @ApiModelProperty(example = "4.3", value = "")


  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Host totalHostRatings(Integer totalHostRatings) {
    this.totalHostRatings = totalHostRatings;
    return this;
  }

  /**
   * Get totalHostRatings
   * @return totalHostRatings
  **/
  @ApiModelProperty(example = "6", value = "")


  public Integer getTotalHostRatings() {
    return totalHostRatings;
  }

  public void setTotalHostRatings(Integer totalHostRatings) {
    this.totalHostRatings = totalHostRatings;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Host host = (Host) o;
    return Objects.equals(this.hostName, host.hostName) &&
        Objects.equals(this.hostAge, host.hostAge) &&
        Objects.equals(this.hostGender, host.hostGender) &&
        Objects.equals(this.isPhoneNumberVerified, host.isPhoneNumberVerified) &&
        Objects.equals(this.isGovtIdverified, host.isGovtIdverified) &&
        Objects.equals(this.rating, host.rating) &&
        Objects.equals(this.totalHostRatings, host.totalHostRatings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostName, hostAge, hostGender, isPhoneNumberVerified, isGovtIdverified, rating, totalHostRatings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Host {\n");
    
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("    hostAge: ").append(toIndentedString(hostAge)).append("\n");
    sb.append("    hostGender: ").append(toIndentedString(hostGender)).append("\n");
    sb.append("    isPhoneNumberVerified: ").append(toIndentedString(isPhoneNumberVerified)).append("\n");
    sb.append("    isGovtIdverified: ").append(toIndentedString(isGovtIdverified)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    totalHostRatings: ").append(toIndentedString(totalHostRatings)).append("\n");
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

