package com.gslab.parivahan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gslab.parivahan.dao.IUserDao;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.EmailMessageBody;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.util.EmailValidator;
import com.gslab.parivahan.util.MessageTemplates;

@Service
public class UserService implements IUserService {
	

	@Autowired
	IUserDao userDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	IRideRequestService rideRequestService;

	@Override
	public List<UserVO> getAllUsers(){
		List<UserVO> userVOs = new ArrayList<>();

		List<User> users = userDao.getAllUsers();
		if(users.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		//throw new RuntimeException("No Users found");
		for (User user : users) {
			userVOs.add(getUseVOFromUser(user));
		}
		return userVOs;
	}

	private UserVO getUseVOFromUser(User user) {
		UserVO userVO = new UserVO(user.getEmail(), user.getMobileNumber(), user.getGender(),user.getFirstName(),
				user.getLastName(),user.getUsername());
		return userVO;

	}

	@Override
	public UserVO addUser(UserVO userVO){
		if (!EmailValidator.validateEmail(userVO.getEmail())) {
			throw new RuntimeException("Email id is not valid");
		}
		
		if(userVO.getMobileNumber().length() != 10) {
			throw new RuntimeException("Mobile Number should be 10 digit");
		}
		userVO.setEmail(userVO.getEmail().trim().toLowerCase());
		User user = userDao.addUser(userVO);
		userVO =new UserVO(user.getEmail(), user.getMobileNumber(), user.getGender(), user.getFirstName(), user.getLastName(),user.getUsername());
		try {
			sendNotificationOnSuccessfulRegistration(userVO);
		} catch (Exception e) {
			//TODO Add logging
			//throw new RuntimeException(e.getMessage());
		}
		return userVO;
	}

	@Override
	public UserVO getUserByEmail(String email){
		email = email.trim().toLowerCase();
		User user = userDao.getUserByEmail(email);
		
		if(user!=null) {
			return new UserVO(user.getEmail(), user.getMobileNumber(), user.getGender(), user.getFirstName(),
					user.getLastName(),user.getUsername());
		}
		return null;
	}
	
	private void sendNotificationOnSuccessfulRegistration(UserVO userVO) {
		String message = String.format(MessageTemplates.SUCCESSFULREGISTRATION);
		notificationService.sendSms(userVO.getMobileNumber(), message);
		EmailMessageBody emailBody = generateEmailBody(userVO.getEmail(),
				null, userVO.getName(), "email/registrationSuccess",
				"Registration Successful on Parivahan Portal", userVO.getName(),null);
		notificationService.sendEmail(emailBody);
	}
	
	private EmailMessageBody generateEmailBody(String emailId,Integer bookingCode,String name,String templateName,String subject,String riderName,Integer offerCode) {
		EmailMessageBody email = new EmailMessageBody();
		List<String> toList = new ArrayList<String>();
		toList.add(emailId);
		email.setTo(toList);
		if(null != bookingCode)
			email.setBookingid(bookingCode);
		email.setHostName(name);
		email.setBody("Test");
		email.setTemplateName(templateName);
		email.setSubject(subject);
		if(!StringUtils.isBlank(riderName)) {
			email.setRiderName(riderName);
		}
		if(null != offerCode)
			email.setOfferid(offerCode);
		return email;
	}
	
	@Override
	public UserVO getUserByName(String name){
		User user = userDao.getUserByName(name);
		
		if(user!=null) {
			return new UserVO(user.getEmail(), user.getMobileNumber(), user.getGender(), user.getFirstName(),
					user.getLastName(),user.getUsername());
		}
		return null;
	}

	@Override
	public List<RideRequestVO> getBookingOfUser(String email, String type) {
		List<RideRequestVO> rideRequests = rideRequestService.getRideRequestByUser(email, type);
		return rideRequests;
	}

	@Override
	public List<Ride> getOffersOfUser(String email) {
		List<Ride> rides=rideRequestService.getOffersByUser(email);
		return rides;
	}

	@Override
	public UserVO getUserByUsername(String username) {
		username =  username.trim();
		User user = userDao.getUserByUsername(username);
		if(user!=null) {
			return new UserVO(user.getEmail(), user.getMobileNumber(), user.getGender(), user.getFirstName(),
					user.getLastName(),user.getUsername());
		}
		return null;
		
	}
}
