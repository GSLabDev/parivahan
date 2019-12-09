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
package com.gslab.parivahan.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.gslab.parivahan.model.DirectionPaths;
import com.gslab.parivahan.model.LatLngDetail;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideParams;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.model.RideSearchCriteria;
import com.gslab.parivahan.model.ShuttleScheduleVO;

import io.swagger.annotations.ApiParam;

public interface IDirectionsService {
	DirectionsResult getDirections(String startLocation, String endLocation, boolean isWalking, boolean alternativeRoutes);

	DistanceMatrix getDistanceMatrix(String[] origins, String[] destinations);

	List<LatLngDetail> getClosestPointsForARide(String rideEncodedPolyline, RideSearchCriteria rideSearchCriteria);

	Ride addUserRide(Ride userRide, boolean showDetailsOnly);

	List<Ride> getUserRides();

	List<Ride> getFilteredUserRides(RideSearchCriteria rideSearchCriteria);

	List<RideSearch> searchRides(RideSearchCriteria rideSearchCriteria);

	void deleteUserRide(Integer offerCode);

	List searchPlaces(String query, String lat, String lng);

	Ride getRideWithRequests(Integer offerCode, String userEmail);

	RideSearch getFilteredRidesWithParams(String rideid, RideParams rideParams);

	RideSearch getSearchResultForARide(RideSearchCriteria rideSearchCriteria, Ride eachRide);

	List<DirectionPaths> getDirectionDetailsWithAlternatives(String startLocation, String endLocation);
	
	List<Ride> getRecentUserRides(String emailId);

	public int countTotalRides();
	
	public List<Ride> getShuttleByDate(String date);

	ShuttleScheduleVO addShuttleSchedule(ShuttleScheduleVO shuttleScheduleVO);
}
