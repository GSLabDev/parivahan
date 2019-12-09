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
 * Vehical
 */
@Validated

public class Vehical   {
  @JsonProperty("typeOfVehicle")
  private String typeOfVehicle = null;

  @JsonProperty("model")
  private String model = null;

  @JsonProperty("registartionNumber")
  private String registartionNumber = null;

  @JsonProperty("colour")
  private String colour = null;

  @JsonProperty("pricingPerKm")
  private Integer pricingPerKm = null;

  @JsonProperty("Description")
  private String description = null;

  @JsonProperty("isHelmateAvailable")
  private Boolean isHelmateAvailable = null;

  public Vehical typeOfVehicle(String typeOfVehicle) {
    this.typeOfVehicle = typeOfVehicle;
    return this;
  }

  /**
   * Get typeOfVehicle
   * @return typeOfVehicle
  **/
  @ApiModelProperty(example = "car", value = "")


  public String getTypeOfVehicle() {
    return typeOfVehicle;
  }

  public void setTypeOfVehicle(String typeOfVehicle) {
    this.typeOfVehicle = typeOfVehicle;
  }

  public Vehical model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
  **/
  @ApiModelProperty(example = "Hundai", value = "")


  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Vehical registartionNumber(String registartionNumber) {
    this.registartionNumber = registartionNumber;
    return this;
  }

  /**
   * Get registartionNumber
   * @return registartionNumber
  **/
  @ApiModelProperty(example = "MH 04 AB 1234", value = "")


  public String getRegistartionNumber() {
    return registartionNumber;
  }

  public void setRegistartionNumber(String registartionNumber) {
    this.registartionNumber = registartionNumber;
  }

  public Vehical colour(String colour) {
    this.colour = colour;
    return this;
  }

  /**
   * Get colour
   * @return colour
  **/
  @ApiModelProperty(example = "white", value = "")


  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public Vehical pricingPerKm(Integer pricingPerKm) {
    this.pricingPerKm = pricingPerKm;
    return this;
  }

  /**
   * Get pricingPerKm
   * @return pricingPerKm
  **/
  @ApiModelProperty(example = "3", value = "")


  public Integer getPricingPerKm() {
    return pricingPerKm;
  }

  public void setPricingPerKm(Integer pricingPerKm) {
    this.pricingPerKm = pricingPerKm;
  }

  public Vehical description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(example = "Car is well maintained and timel serviced.", value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Vehical isHelmateAvailable(Boolean isHelmateAvailable) {
    this.isHelmateAvailable = isHelmateAvailable;
    return this;
  }

  /**
   * Get isHelmateAvailable
   * @return isHelmateAvailable
  **/
  @ApiModelProperty(example = "false", value = "")


  public Boolean isIsHelmateAvailable() {
    return isHelmateAvailable;
  }

  public void setIsHelmateAvailable(Boolean isHelmateAvailable) {
    this.isHelmateAvailable = isHelmateAvailable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vehical vehical = (Vehical) o;
    return Objects.equals(this.typeOfVehicle, vehical.typeOfVehicle) &&
        Objects.equals(this.model, vehical.model) &&
        Objects.equals(this.registartionNumber, vehical.registartionNumber) &&
        Objects.equals(this.colour, vehical.colour) &&
        Objects.equals(this.pricingPerKm, vehical.pricingPerKm) &&
        Objects.equals(this.description, vehical.description) &&
        Objects.equals(this.isHelmateAvailable, vehical.isHelmateAvailable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeOfVehicle, model, registartionNumber, colour, pricingPerKm, description, isHelmateAvailable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vehical {\n");
    
    sb.append("    typeOfVehicle: ").append(toIndentedString(typeOfVehicle)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    registartionNumber: ").append(toIndentedString(registartionNumber)).append("\n");
    sb.append("    colour: ").append(toIndentedString(colour)).append("\n");
    sb.append("    pricingPerKm: ").append(toIndentedString(pricingPerKm)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isHelmateAvailable: ").append(toIndentedString(isHelmateAvailable)).append("\n");
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

