package com.gslab.parivahan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */
public class ParivahanLoginRequest {

	private String username;
	private String password;

	@JsonCreator
	public ParivahanLoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
