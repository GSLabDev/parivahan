package com.gslab.parivahan.dbmodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="ride_user")
public class User extends ParivahanAudingInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="username")
	private String username;
	@Column(name="user_email")
	private String email;
	@Column(name="name")
	private String name;
	@Column(name="mobile_number")
	private String mobileNumber;
	@Column(name="gender")
	private String gender;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String email, String mobileNumber, String gender, String firstName, String lastName,String username) {
		super();
		this.setEmail(email);
		if(!StringUtils.isBlank(firstName) && !StringUtils.isBlank(lastName))
			this.name = firstName+" "+lastName;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		Date currentDate = new Date();
		this.setCreatedDate(currentDate);;
		this.setUpdatedDate(currentDate);
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(StringUtils.isNotBlank(email)) {
			this.email =email.trim().toLowerCase();
		}
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

	public void setUsername(String gsId) {
		this.username = gsId;
	}
	
	
	
	
}
