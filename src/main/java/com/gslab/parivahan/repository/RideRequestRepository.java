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
