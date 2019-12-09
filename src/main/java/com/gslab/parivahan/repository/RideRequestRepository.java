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
package com.gslab.parivahan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.UserVO;

public interface RideRequestRepository extends CrudRepository<RideRequest, Long>{

	@Query(value = "select * from ride_request as rr where rr.is_deleted=false AND ride_id IN (select id from ride where offer_code =:offer_code)", nativeQuery = true)
	List<RideRequest> getAllRidesRequestsByOfferCode(@Param("offer_code") int OfferCode);
	
	@Query(value="Select rr from RideRequest rr where isDeleted=false AND bookingCode=:bookingCode")
	RideRequest findByBookingCode(@Param("bookingCode")Integer bookingCode);

	@Query(value="select r.* from ride_request r where r.is_deleted=false AND r.ride_id=:id",nativeQuery=true)
	List<RideRequest> getRideRequestByRideId(@Param("id") long id);

	@Modifying
	@Query(value="update RideRequest set isDeleted=true where Id=:id")
	void deleteRideRequest(@Param("id") Integer rideRequestId);
	
	@Query(value="Select rr from RideRequest rr where isDeleted=false AND bookingId=:bookingId")
	RideRequest findByBookingId(@Param("bookingId")String bookingId);

	@Query(value="Select rr from RideRequest rr where isDeleted=false AND rr.user=:user")
	List<RideRequest> findRequestsByUser(@Param("user")User user);
}
