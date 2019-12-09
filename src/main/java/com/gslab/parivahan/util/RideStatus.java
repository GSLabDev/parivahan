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
