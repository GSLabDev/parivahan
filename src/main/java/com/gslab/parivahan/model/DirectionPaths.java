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
