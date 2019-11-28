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
