package com.gslab.parivahan.util;


public enum RideStatus {

	ACCEPTED("ACCEPTED"),DECLINED("DECLINED"),PENDING("PENDING"),CANCELLED("CANCELLED");
	
	public static boolean contains(String test) {

		for (RideStatus c : RideStatus.values()) {
			if (c.name().equals(test)) {
				return true;
			}
		}

		return false;
	}
	
	private final String statusType;

	private RideStatus(String type) {
		statusType = type;
	}

	public String toString() {
		return this.statusType;
	}
	
	
}
