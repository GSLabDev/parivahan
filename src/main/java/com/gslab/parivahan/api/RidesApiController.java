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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.model.LdapUser;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.service.IDirectionsService;
import com.gslab.parivahan.service.UserService;

import io.swagger.annotations.ApiParam;

@Controller
public class RidesApiController implements RidesApi {

    private static final Logger log = LoggerFactory.getLogger(RidesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private IDirectionsService directionsService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private Environment env;

    @org.springframework.beans.factory.annotation.Autowired
    public RidesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Ride>> getAllRides(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	List<Ride> rides = directionsService.getUserRides();
                return new ResponseEntity<List<Ride>>(rides, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Ride>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Ride>>(HttpStatus.NOT_IMPLEMENTED);
    }
    
	public ResponseEntity<List<Ride>> getRecentRides(
			@ApiParam(value = "", required = true, defaultValue = "application/json") @RequestHeader(value = "Content-Type", required = true) String contentType,
			@ApiParam(value = "", required = true) @RequestParam int pageSize,
			@ApiParam(value = "", required = true) @RequestParam int startIndex,
			@ApiParam(value = "", required = false) @RequestParam String email) {
		String accept = request.getHeader("Accept");
		List<Ride> paginatedRides = new ArrayList<Ride>();
		// UserVO user = ((LdapUser)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserVO();
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
			ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal());
			email = userService.getUserByUsername(usercontext.getUsername()).getEmail();
		}
		if (StringUtils.isBlank(email))
			return null;
		if (accept != null && accept.contains("application/json")) {
			try {
				List<Ride> rides = directionsService.getRecentUserRides(email);
				if (startIndex < rides.size() && startIndex + pageSize < rides.size()) {
					paginatedRides = rides.subList(startIndex, startIndex + pageSize);
				} else if (startIndex < rides.size() && startIndex + pageSize > rides.size()) {
					paginatedRides = rides.subList(startIndex, rides.size());
				} else if (startIndex > rides.size()) {
					paginatedRides = Collections.EMPTY_LIST;
				}
				return new ResponseEntity<List<Ride>>(paginatedRides, HttpStatus.OK);
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				return new ResponseEntity<List<Ride>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<List<Ride>>(HttpStatus.NOT_IMPLEMENTED);
	}
    
    public ResponseEntity<Integer> countTotalRides(@ApiParam(value = "" ,required=true, defaultValue="application/json") @RequestHeader(value="Content-Type", required=true) String contentType) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	int totalRides = directionsService.countTotalRides();
                return new ResponseEntity<Integer>(totalRides, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Integer>(HttpStatus.NOT_IMPLEMENTED);
    }

}