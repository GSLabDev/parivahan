package com.gslab.parivahan.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.util.RideConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
public interface UserProfileApi {

	@ApiOperation(value = "get shuttle Details", nickname = "getShuttleDetails", notes = "Api will Return shuttle details", response = RideSearch.class, tags = {
			"developers", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK.", response = RideSearch.class),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Server, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/booking", produces = { "application/json" }, method = RequestMethod.GET)
	List<RideRequestVO> getUserBookings(@RequestParam(required=false) String type,@RequestParam(required=false) String email);
	
	@ApiOperation(value = "get shuttle Details", nickname = "getShuttleDetails", notes = "Api will Return shuttle details", response = RideSearch.class, tags = {
			"developers", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK.", response = RideSearch.class),
			@ApiResponse(code = 400, message = "Bad Request, All parameters are mandatory."),
			@ApiResponse(code = 404, message = "Not Found."),
			@ApiResponse(code = 500, message = "Intenal Server, Unknown Error.") })
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/offer", produces = { "application/json" }, method = RequestMethod.GET)
	List<Ride> getUserOffers(@RequestParam(required=false) String email);

	Map<String, Object> getAllowedEmailSuffix();
}
