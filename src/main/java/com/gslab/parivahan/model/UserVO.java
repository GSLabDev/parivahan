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
package com.gslab.parivahan.model;

import org.apache.commons.lang3.StringUtils;

import com.gslab.parivahan.dbmodel.User;

public class UserVO {

private String email;
private String name;
private String mobileNumber;
private String gender;
private String firstName;
private String lastName;
private String username;

public UserVO() {
	// TODO Auto-generated constructor stub
}

	public UserVO(String email, String mobileNumber, String gender, String firstName, String lastName,String username) {
		super();
		this.email = email;
		if(!StringUtils.isBlank(firstName) && !StringUtils.isBlank(lastName))
			this.name = firstName+" "+lastName;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	
public UserVO(User user) {
	this.email = user.getEmail();
	this.name = user.getName();
	this.mobileNumber = user.getMobileNumber();
	this.gender = user.getGender();
	this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.username = user.getUsername();
}

public String getEmail() {

	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getMobileNumber() {
	return mobileNumber;
}
public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}


}
