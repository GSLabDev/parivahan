package com.gslab.parivahan.model;

import com.google.maps.model.Distance;
import com.google.maps.model.LatLng;

public class LatLngDetail {
	private LatLng latLng;
	private Distance distance;
	private Integer index;

	public LatLngDetail() {
		// TODO Auto-generated constructor stub
	}

	public LatLngDetail(LatLng latLng, Distance distance) {
		super();
		this.latLng = latLng;
		this.distance = distance;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

}
