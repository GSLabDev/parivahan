package com.gslab.parivahan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.User;

public interface RideRepository extends CrudRepository<Ride, Long>{
	@Query(value="select r from Ride r where isDeleted=false AND rideId=:rideId")
	Ride findByRideId(@Param("rideId") String rideId);


	/**
	 * Returns rides for the same day(only one day), greater than the time entered by the user. 
	 * @param departureDate
	 * @param userEmail
	 * @return List of Ride
	 */
	@Query(value = "select r.* from ride r where r.departure_date_time >= :departureDate "
			+ " AND r.departure_date_time <= :depDate "
			+ "AND r.is_deleted=false "
			+ " AND r.user_id <> (select ru.id from ride_user ru where ru.user_email =:userEmail) "
			,nativeQuery=true)
	List<Ride> getRidesForGivenDate(@Param("departureDate") Date departureDate, @Param("depDate") Date searchLimitDateWithoutTime, @Param("userEmail") String userEmail);
	
	@Modifying
	@Query(value="update Ride set isDeleted=true where offerCode=:offerCode")
	void deleteByOfferCode(@Param("offerCode")Integer offerCode);
	
	@Query(value = "select r.* from Ride r where r.is_deleted=false AND id IN (select rq.ride_id from ride_request rq where rq.booking_id=:bookingId)",nativeQuery = true)
	Ride findRideByBookingId(@Param("bookingId")String bookingId);

	@Query(value="select r from Ride r where isDeleted=false AND offerCode=:offerCode")
	Ride findByOfferCode(@Param("offerCode")Integer offerCode);
	
    List<Ride> findTop5ByIsDeletedOrderByCreatedDateDesc(Boolean isDeleted);
    
    List<Ride> findTop5ByIsDeletedOrderByDepartureDateTimeDesc(Boolean isDeleted);
    
	@Query(value = "select r.* from ride r where is_deleted = false AND r.departure_date_time >= :depDate AND r.user_id <> (select ru.id from ride_user ru where ru.user_email =:emailId) order by departure_date_time asc",nativeQuery = true)
	List<Ride> findRecentRideOrderByDepartureDate(@Param("depDate")Date depDate,@Param("emailId")String emailId);

	@Query(value = "select count(ride_id) from ride where is_deleted  = false AND departure_date_time >= :depDate",nativeQuery = true)
	int countTotalRides(@Param("depDate")Date depDate);
	
	@Query(value = "select * from ride where is_deleted  = false AND departure_date_time >= :depDate AND departure_date_time <= :endDate AND vehicle = 'shuttle'",nativeQuery = true)
	List<Ride> getShuttleByDate(@Param("depDate")Date depDate,@Param("endDate")Date endDate);

	@Query(value="select r from Ride r where isDeleted=false AND user=:user")
	List<Ride> getRideByUser(@Param("user") User user);
}
