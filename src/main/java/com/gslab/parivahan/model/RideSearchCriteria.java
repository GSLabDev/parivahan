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

public class RideSearchCriteria {

	private String startLocation;
	private String endLocation;
	private Integer threshold;
	private boolean searchFromBeginning;
	private String date;
	private String hours;
	private String userEmail;
		
	public RideSearchCriteria(String startLocation, String endLocation, Integer threshold, boolean searchFromBeginning,
			String date, String hours,String userEmail) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.threshold = threshold;
		this.searchFromBeginning = searchFromBeginning;
		this.date = date;
		this.hours = hours;
		this.userEmail=userEmail;
	}
	public RideSearchCriteria() {
		// TODO Auto-generated constructor stub
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
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public boolean isSearchFromBeginning() {
		return searchFromBeginning;
	}
	public void setSearchFromBeginning(boolean searchFromBeginning) {
		this.searchFromBeginning = searchFromBeginning;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
