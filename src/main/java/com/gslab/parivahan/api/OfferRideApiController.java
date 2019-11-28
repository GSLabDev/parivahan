package com.gslab.parivahan.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.model.LdapUser;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.Response;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.ShuttleScheduleVO;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.service.IDirectionsService;
import com.gslab.parivahan.service.UserService;

import io.swagger.annotations.ApiParam;

@Controller
public class OfferRideApiController implements OfferRideApi {
	
	@Autowired
    private IDirectionsService directionsService;

	@Autowired
	private UserService userService;
	
    private static final Logger log = LoggerFactory.getLogger(OfferRideApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private Environment env;

    @org.springframework.beans.factory.annotation.Autowired
    public OfferRideApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Ride> offerRide(@RequestParam(value = "email") String email,@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "Ride Details" ,required=true )  @Valid @RequestBody Ride rideParams,@ApiParam(value = "Show ride details only" ,required=false ) @RequestParam(value="showDetailsOnly",required=false,defaultValue="false") boolean showDetailsOnly ) {
    	//UserVO user = ((LdapUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserVO();
    	if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
    	ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		//String email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		 UserVO user = userService.getUserByUsername(usercontext.getUsername());
		if(null != user) {
			email = user.getEmail();
		}
		}
		rideParams.getUserVO().setEmail(email);
    	Ride ride = directionsService.addUserRide(rideParams,showDetailsOnly);
        return new ResponseEntity<Ride>(ride,HttpStatus.OK);
    }

	@Override
	public ResponseEntity<Response> deleteOfferedRide(Integer offerCode) {
		directionsService.deleteUserRide(offerCode);
        return new ResponseEntity<Response>(new Response(200,"Deleted ride with offerCode:"+offerCode),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ShuttleScheduleVO> addShuttleSchedule(@RequestBody ShuttleScheduleVO shuttleScheduleVO) {
		return new ResponseEntity<ShuttleScheduleVO>(directionsService.addShuttleSchedule(shuttleScheduleVO),HttpStatus.OK);
	}

}