package com.gslab.parivahan.util;

public enum Vehicle {

	CAR("car"),BIKE("bike"),SHUTTLE("shuttle");
	
	public static boolean contains(String test) {

		for (Vehicle c : Vehicle.values()) {
			if (c.name().equals(test)) {
				return true;
			}
		}

		return false;
	}
	
	private final String vehicleType;

	private Vehicle(String type) {
		vehicleType = type;
	}

	public String toString() {
		return this.vehicleType;
	}
}
