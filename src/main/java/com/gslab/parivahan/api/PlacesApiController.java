package com.gslab.parivahan.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.service.DirectionsService;

@Controller
public class PlacesApiController implements PlacesApi {

	private static final Logger log = LoggerFactory.getLogger(RidesApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;
	@Autowired
	private DirectionsService directionsService;

	@org.springframework.beans.factory.annotation.Autowired
	public PlacesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public ResponseEntity<List<String>> searchPlaces(String query, String lat, String lng,HttpServletRequest request) {
		List<String> places=directionsService.searchPlaces(query,lat,lng);
		return new ResponseEntity<List<String>>(places, HttpStatus.OK);
	}



}