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
package com.gslab.parivahan.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gslab.parivahan.model.LdapUser;
import com.gslab.parivahan.model.LdapUserTest;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.service.IUserService;
import com.gslab.parivahan.util.RideConstants;

@RestController
public class UserProfileApiController  implements UserProfileApi{
	
	@Autowired
	IUserService userService;
	
	@Value("#{'${parivahan.allowed.email.suffix}'}")
	private String allowedSuffix;
	
	@Autowired
	private Environment env;

	@Override
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/booking", produces = { "application/json" }, method = RequestMethod.GET)
	public List<RideRequestVO> getUserBookings(@RequestParam(required=false) String type,@RequestParam(required=false) String email) {
		
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
		ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		 email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		}
		if (StringUtils.isBlank(email))
			return null;
		List<RideRequestVO> rideRequests = userService.getBookingOfUser(email, type);
		return rideRequests;
	}

	@Override
	@RequestMapping(value =  RideConstants.VERSION_1+"/user/offer", produces = { "application/json" }, method = RequestMethod.GET)
	public List<Ride> getUserOffers(@RequestParam(required=false) String email) {
		//UserVO user = ((LdapUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserVO();
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
		ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		}
		if (StringUtils.isBlank(email))
			return null;
		List<Ride> rides = userService.getOffersOfUser(email);
		return rides;
	}
	
	@Override
	@RequestMapping(value =  RideConstants.VERSION_1+"/allowed-email-suffix", produces = { "application/json" }, method = RequestMethod.GET)
	public Map<String,Object> getAllowedEmailSuffix() {
		HashMap<String, Object> allowedSuffixMap = new HashMap<String,Object>();
		List<String> allowedEmail = new ArrayList<String>();
		if(allowedSuffix.contains(",")) {
			allowedEmail = Arrays.asList(allowedSuffix.split(","));
		}else {
			allowedEmail.add(allowedSuffix);
		}
		allowedSuffixMap.put("AllowedEmail",allowedEmail);
		return allowedSuffixMap;
	}
}
