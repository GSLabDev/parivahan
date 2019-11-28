package com.gslab.parivahan.model;

import java.util.List;

import com.google.maps.model.LatLng;

public class DirectionPaths {
	private String summary;
	private List<LatLng> paths;
	
	public DirectionPaths() {
		// TODO Auto-generated constructor stub
	}
	
	
	public DirectionPaths(String summary, List<LatLng> paths) {
		super();
		this.summary = summary;
		this.paths = paths;
	}


	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List<LatLng> getPaths() {
		return paths;
	}
	public void setPaths(List<LatLng> paths) {
		this.paths = paths;
	}
}
