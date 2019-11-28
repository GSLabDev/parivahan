package com.gslab.parivahan.api;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.util.RideConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "RideRequest", description = "the RideRequest API")
@RequestMapping("/api")
public interface RideRequestApi {

	@ApiOperation(value = "addRideRequest", nickname = "addRideRequest", notes = "Add Ride Request", response = RideRequest.class, tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value = RideConstants.VERSION_1+"/booking", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<RideRequestVO> addRideRequest(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestBody RideRequestVO rideRequest,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);

	@ApiOperation(value = "GetAllRideRequest", nickname = "GetAllRideRequest", notes = "Get All Ride Request", response = RideRequest.class, responseContainer = "List", tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/booking", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<RideRequest>> getAllRidesRequests();

	@ApiOperation(value = "GetAllRideRequestByBookingId", nickname = "GetAllRideRequestByBookingId", notes = "Get All Ride Request by Booking Id", response = RideRequest.class, tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/booking/{bookingCode}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<RideRequestVO> getRideRequestsByBookingCode(@PathVariable Integer bookingCode,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);

	@ApiOperation(value = "GetAllRideRequestByOfferId", nickname = "GetAllRideRequestByOfferId", notes = "Get All Ride Request By offer id", response = RideRequest.class, responseContainer = "List", tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/offer/{offerCode}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Ride> getRideRequestsByOfferCode(@PathVariable Integer offerCode,@RequestParam(value = "email", required=false) String email,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);

	@ApiOperation(value = "UpdateBookingStatus", nickname = "UpdateBookingStatus", notes = "Update Booking Status", response = RideRequest.class, tags = {
			"developers", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK.", response = Ride.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Sevrer, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/booking/{bookingId}", produces = { "application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<RideRequestVO> updateRideRequestStatus(@RequestParam(required = true) String rideStatus,
			@PathVariable String bookingId,
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType);
}
