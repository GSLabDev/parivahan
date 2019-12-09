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
package com.gslab.parivahan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gslab.parivahan.dao.IRideDao;
import com.gslab.parivahan.dao.IRideRequestDao;
import com.gslab.parivahan.dao.IUserDao;
import com.gslab.parivahan.dbmodel.Ride;
import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.model.EmailMessageBody;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.model.RideSearchCriteria;
import com.gslab.parivahan.util.DateUtil;
import com.gslab.parivahan.util.MessageTemplates;
import com.gslab.parivahan.util.RideCodeGenerator;
import com.gslab.parivahan.util.RideStatus;
import com.gslab.parivahan.util.Vehicle;

@Service
public class RideRequestService implements IRideRequestService {

	@Autowired
	private IRideRequestDao rideRequestDao;

	@Autowired
	private IDirectionsService directionService;

	@Autowired
	private IRideDao rideDao;

	@Autowired
	IUserDao userDao;

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private InviteCreationService ics;

	public RideRequestVO addRideRequest(RideRequestVO rideRequestVo) {
		Ride ride = rideDao.getRideByRideId(rideRequestVo.getRideId());
		if (null == ride) {
			throw new RuntimeException("Ride not found");
		}
		if (checkIfSameUserIsBookingTheRide(ride, rideRequestVo)) {
			throw new RuntimeException("Cannot book your own ride");
		}
		
		User user = userDao.getUserByEmail(rideRequestVo.getUserEmail());
		if (user == null) {
			throw new RuntimeException("User not found");
		}
	
		//Check if existing ride request with Accepted or pending status exists in DB for the same user
		List<RideRequest> existingRideRequest = rideRequestDao.getRideRequestByRideId(ride.getId());
		if(!CollectionUtils.isEmpty(existingRideRequest)) {
			for (RideRequest rideRequest : existingRideRequest) {
				if(rideRequest.getUser().getEmail().equals(rideRequestVo.getUserEmail())) {
					if(RideStatus.ACCEPTED.equals(rideRequest.getStatus()) || 
							RideStatus.PENDING.equals(rideRequest.getStatus())) {
						throw new RuntimeException("Existing request with status: "+rideRequest.getStatus() +" is already present!");
					}
				}
			}
		}

		RideRequest rideRequest = new RideRequest();
		rideRequest.setUser(user);
		rideRequest.setRideId(ride);

		rideRequest.setStartLocation(rideRequestVo.getStartLocation());
		rideRequest.setEndLocation(rideRequestVo.getEndLocation());
		
		rideRequest.setBookingCode(RideCodeGenerator.generateRandomNumber());
		rideRequest.setBookingId(RideCodeGenerator.generateRandomId());
		if(!ride.getVehicle().toString().equalsIgnoreCase(Vehicle.SHUTTLE.toString())) {
			// search again for the same origin and dest to calculate cost
			RideSearch searchResult = getSearchResults(rideRequestVo, ride);
			rideRequest.setStatus(RideStatus.PENDING);
			if(searchResult.getTotalChargesForRide()<DirectionsService.MINIMUM_RIDE_CHARGE) {
				rideRequest.setRideCharges(DirectionsService.MINIMUM_RIDE_CHARGE);
			}else {
				rideRequest.setRideCharges(searchResult.getTotalChargesForRide());
			}
		}else {
			rideRequest.setStatus(RideStatus.PENDING);
			rideRequest.setRideCharges(0);
		}
		
		rideRequest.setIsDeleted(false);
		rideRequest = rideRequestDao.addRideRequest(rideRequest);
		try {
			if (!ride.getVehicle().toString().equalsIgnoreCase(Vehicle.SHUTTLE.toString())) {

				sendNotificationOnBookingRide(rideRequest, ride);

			} else {
				//update ride request and set it to accepted state and decrement the ride count
				this.updateRideRequestStatus(RideStatus.ACCEPTED.toString(), rideRequest.getBookingId(),Vehicle.SHUTTLE.toString());
				sendNotificationOnRideAcceptForRider(rideRequest, rideRequest.getBookingCode(), ride);

			}
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage());
		}
		// TODO convert to vo response
		return new RideRequestVO(rideRequest);
	}

	private boolean checkIfSameUserIsBookingTheRide(Ride ride, RideRequestVO rideRequestVo) {
		if (ride.getUser() != null && ride.getUser().getEmail()!=null && rideRequestVo.getUser() != null
				&& ride.getUser().getEmail().equals(rideRequestVo.getUser().getEmail())) {
			return true;
		} else
			return false;
	}

	private RideSearch getSearchResults(RideRequestVO rideRequestVo, Ride ride) {
		RideSearchCriteria rideSearchCriteria = new RideSearchCriteria();
		rideSearchCriteria.setStartLocation(rideRequestVo.getStartLocation());
		rideSearchCriteria.setEndLocation(rideRequestVo.getEndLocation());

		RideSearch searchResult = directionService.getSearchResultForARide(rideSearchCriteria,
				new com.gslab.parivahan.model.Ride(ride));
		return searchResult;
	}

	public List<RideRequest> getAllRideRequest() {
		List<RideRequest> rides = new ArrayList<RideRequest>();
		rides = rideRequestDao.getAllRideRequest();
		if (rides.isEmpty()) {
			throw new RuntimeException("No Ride Request Found");
		}
		return rides;
	}

	public List<RideRequest> getRideRequestByOfferCode(Integer offerId) {
		List<RideRequest> rideRequest = rideRequestDao.getAllRideRequestByOfferCode(offerId);
		return rideRequest;
	}

	public RideRequestVO getRideRequestByBookingCode(Integer bookingCode) {
		RideRequest rideRequest = rideRequestDao.getRideRequestByBookingCode(bookingCode);
		if (null == rideRequest) {
			throw new RuntimeException("No ride request found for this booking code");
		}
		return new RideRequestVO(rideRequest);
	}

	public RideRequestVO updateRideRequestStatus(String rideStatus, String bookingId,String vehicle) {
		RideRequest rideRequest = rideRequestDao.getRideRequestByBookingId(bookingId);
		if (null == rideRequest) {
			throw new RuntimeException("No Bookings found");
		}
		String status = rideRequest.getStatus().toString();
		rideRequest = rideRequestDao.updateRideRequestStatus(rideStatus, bookingId, rideRequest);
		Ride ride = rideDao.getRideByRideId(rideRequest.getRide().getRideId());
		if (null == ride) {
			throw new RuntimeException("No ride found");
		}
		try {
			if (rideStatus.equalsIgnoreCase(RideStatus.ACCEPTED.toString())) {
				//Do not send notification when vehicle is shuttle
				if(!Vehicle.SHUTTLE.toString().equals(vehicle)) {
					sendNotificationOnRideAccept(rideRequest, rideRequest.getBookingCode(), ride);	
				}
				
			} else if (rideStatus.equalsIgnoreCase(RideStatus.DECLINED.toString())) {
				sendNotificationOnRideReject(rideRequest, rideRequest.getBookingCode(), ride);
			} else if (rideStatus.equalsIgnoreCase(RideStatus.CANCELLED.toString())) {
				if (RideStatus.ACCEPTED.toString().equalsIgnoreCase(status)) {
					sendNotificationOnBookingCancellationForRider(rideRequest, rideRequest.getBookingCode(), ride);
					sendNotificationOnBookingCancellationForHost(rideRequest, rideRequest.getBookingCode(), ride);
				} else if (RideStatus.PENDING.toString().equalsIgnoreCase(status)) {
					sendNotificationOnBookingCancellationForRider(rideRequest, rideRequest.getBookingCode(), ride);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RideRequestVO(rideRequest);
	}

	private void sendNotificationOnBookingRide(RideRequest rideRequest, Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForRider = String.format(MessageTemplates.BOOKRIDEFORRIDER, rideRequest.getBookingCode(),
				startLocation, endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				ride.getUser().getMobileNumber(), rideRequest.getRideCharges());
		String messageForHost = String.format(MessageTemplates.BOOKRIDEFORHOST, ride.getOfferCode(), startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getUser().getMobileNumber(), rideRequest.getRideCharges());
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		notificationService.sendSms(ride.getUser().getMobileNumber(), messageForHost);

		EmailMessageBody emailBodyForRider = generateEmailBody(rideRequest.getUser().getEmail(),
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), "email/bookRideForRider",
				"Ride is requested From " + startLocation + " to " + endLocation, ride.getUser().getName(), null,
				rideRequest.getStartLocation(), rideRequest.getEndLocation(),
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), null,null);
		notificationService.sendEmail(emailBodyForRider);
		EmailMessageBody emailBodyForHost = generateEmailBody(ride.getUser().getEmail(), null, ride.getUser().getName(),
				"email/bookRideForHost", "Ride is requested From " + startLocation + " to " + endLocation,
				rideRequest.getUser().getName(), ride.getOfferCode(), rideRequest.getStartLocation(),
				rideRequest.getEndLocation(), DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), null,null);
		notificationService.sendEmail(emailBodyForHost);
	}

	private void sendNotificationOnRideAccept(RideRequest rideRequest, Integer bookingCode, Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForRider = String.format(MessageTemplates.ACCEPTRIDEForRIDER, bookingCode, startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getUser().getMobileNumber(), rideRequest.getRideCharges());
		String messageForHost = String.format(MessageTemplates.ACCEPTRIDEFORHOST, ride.getOfferCode(), startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getUser().getMobileNumber(), rideRequest.getRideCharges());
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		notificationService.sendSms(ride.getUser().getMobileNumber(), messageForHost);
		File file = ics.write(rideRequest.getUser().getEmail(), "Ride Scheduled from " + startLocation + " to " + endLocation,
				DateUtil.ConvertDateToInvitationFormat(ride.getDepartureDateTime()),
				DateUtil.ConvertDateToInvitationFormat(DateUtil.addHours(ride.getDepartureDateTime(), 1)));
		EmailMessageBody emailBodyForRider = generateEmailBody(rideRequest.getUser().getEmail(),
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), "email/acceptRideForRider",
				"Ride Request accepted From " + startLocation + " to " + endLocation, ride.getUser().getName(), null,
				rideRequest.getStartLocation(), rideRequest.getEndLocation(),
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), null,getByteArray(rideRequest.getUser().getEmail()));
		notificationService.sendEmail(emailBodyForRider);
		EmailMessageBody emailBodyForHost = generateEmailBody(ride.getUser().getEmail(), null, ride.getUser().getName(),
				"email/acceptRideForHost", "Ride Request accepted From " + startLocation + " to " + endLocation,
				rideRequest.getUser().getName(), ride.getOfferCode(), rideRequest.getStartLocation(),
				rideRequest.getEndLocation(), DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), ride.getAvailableSeats(),null);
		notificationService.sendEmail(emailBodyForHost);
		file.delete();
	}
	
	private void sendNotificationOnRideAcceptForRider(RideRequest rideRequest, Integer bookingCode, Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForRider = String.format(MessageTemplates.ACCEPTRIDEForRIDER, bookingCode, startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getUser().getMobileNumber(), rideRequest.getRideCharges());
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		File file = ics.write(rideRequest.getUser().getEmail(), "Ride Scheduled from " + startLocation + " to " + endLocation,
				DateUtil.ConvertDateToInvitationFormat(ride.getDepartureDateTime()),
				DateUtil.ConvertDateToInvitationFormat(DateUtil.addHours(ride.getDepartureDateTime(), 1)));
		EmailMessageBody emailBodyForRider = generateEmailBody(rideRequest.getUser().getEmail(),
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), "email/acceptRideForRider",
				"Ride Request accepted From " + startLocation + " to " + endLocation, ride.getUser().getName(), null,
				rideRequest.getStartLocation(), rideRequest.getEndLocation(),
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), null,getByteArray(rideRequest.getUser().getEmail()));
		notificationService.sendEmail(emailBodyForRider);
		file.delete();
	}

	
	private EmailMessageBody generateEmailBody(String emailId, Integer bookingCode, String name, String templateName,
			String subject, String riderName, Integer offerCode, String startLocation, String endLocation,
			String dateAndTime, String cost, Integer availableSeats,byte[] inviteFile) {
		EmailMessageBody email = new EmailMessageBody();
		List<String> toList = new ArrayList<String>();
		toList.add(emailId);
		email.setTo(toList);
		if (null != bookingCode)
			email.setBookingid(bookingCode);
		email.setHostName(name);
		email.setBody("Test");
		email.setTemplateName(templateName);
		email.setSubject(subject);
		if (!StringUtils.isBlank(riderName)) {
			email.setRiderName(riderName);
		}
		if (null != offerCode)
			email.setOfferid(offerCode);
		email.setStartLocation(startLocation);
		email.setEndLocation(endLocation);
		email.setDateAndTime(dateAndTime);
		email.setCost(cost);
		if (null != availableSeats) {
			email.setAvailableSeats(availableSeats);
		}
		if(null!=inviteFile) {
			email.setInvite(inviteFile);
		}
		return email;
	}

	@Override
	public void deleteRequest(RideRequest rideRequest) {
		rideRequestDao.deleteRideRequest(rideRequest);
	}

	private void sendNotificationOnRideReject(RideRequest rideRequest, Integer bookingCode, Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForRider = String.format(MessageTemplates.REJECTRIDEFORRIDER, bookingCode, startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		String messageForHost = String.format(MessageTemplates.REJECTRIDEFORHOST, startLocation, endLocation,
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()), ride.getOfferCode());
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		notificationService.sendSms(ride.getUser().getMobileNumber(), messageForHost);

		EmailMessageBody emailBodyforRider = generateEmailBody(rideRequest.getUser().getEmail(),
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), "email/rejectRideToRider",
				"Ride Request Rejected From " + startLocation + " to " + endLocation, ride.getUser().getName(), null,
				startLocation, endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()), null,
				null,null);
		notificationService.sendEmail(emailBodyforRider);
		EmailMessageBody emailBodyForHost = generateEmailBody(ride.getUser().getEmail(), null, ride.getUser().getName(),
				"email/rejectRideToHost", "You have rejected the ride From " + startLocation + " to " + endLocation,
				rideRequest.getUser().getName(), ride.getOfferCode(), startLocation, endLocation,
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				rideRequest.getRideCharges().toString(), ride.getAvailableSeats(),null);
		notificationService.sendEmail(emailBodyForHost);
	}

	private void sendNotificationOnBookingCancellationForRider(RideRequest rideRequest, Integer bookingCode,
			Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForRider = String.format(MessageTemplates.CANCELBOOKINGFORRIDER, bookingCode, startLocation,
				endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		String messageForHost = String.format(MessageTemplates.CANCELBOOKINGFORHOST, ride.getOfferCode(), bookingCode,
				startLocation, endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		EmailMessageBody emailBodyforRider = generateEmailBody(rideRequest.getUser().getEmail(),
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), "email/cancelBookingForRider",
				"Booking Cancelled for Ride With " + startLocation + " to " + endLocation, ride.getUser().getName(),
				null, startLocation, endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()),
				null, null,null);
		notificationService.sendEmail(emailBodyforRider);
	}

	private void sendNotificationOnBookingCancellationForHost(RideRequest rideRequest, Integer bookingCode, Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		String messageForHost = String.format(MessageTemplates.CANCELBOOKINGFORHOST, ride.getOfferCode(), bookingCode,
				startLocation, endLocation, DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		notificationService.sendSms(ride.getUser().getMobileNumber(), messageForHost);
		EmailMessageBody emailBodyForHost = generateEmailBody(ride.getUser().getEmail(), null, ride.getUser().getName(),
				"email/cancelBookingForHost", "Booking Cancelled for Ride With " + startLocation + " to " + endLocation,
				rideRequest.getUser().getName(), ride.getOfferCode(), startLocation, endLocation,
				DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()), null, ride.getAvailableSeats(),null);
		notificationService.sendEmail(emailBodyForHost);
	}

	private String trimLocation(String location) {
		if (location.length() < 10) {
			return location;
		} else {
			return location.substring(0, 10) + "...";
		}
	}
	private byte[] getByteArray(String email) {
		String filePath = env.getProperty("gslab.support.email.invitation.path")+email+".ics";
		File file = new File(filePath);
		byte[] bytesArray = new byte[(int) file.length()]; 
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.read(bytesArray);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesArray;
	}

	@Override
	public List<RideRequestVO> getRideRequestByUser(String email, String vehicleType) {
		List<RideRequestVO> rideRequestsVO=new ArrayList<RideRequestVO>();
		User user = userDao.getUserByEmail(email);
		List<RideRequest> rideRequests = rideRequestDao.getRideRequestByUser(user);
		
		if (StringUtils.isBlank(vehicleType)) {
			for (RideRequest rideRequest : rideRequests) {
					rideRequestsVO.add(new RideRequestVO(rideRequest));
			}
		} else {
			for (RideRequest rideRequest : rideRequests) {
				if(vehicleType.equals(rideRequest.getRide().getVehicle())) {
					rideRequestsVO.add(new RideRequestVO(rideRequest));
				}
			}
		}
		return rideRequestsVO;
	}

	@Override
	public List<com.gslab.parivahan.model.Ride> getOffersByUser(String email) {
		List<com.gslab.parivahan.model.Ride> rides=new ArrayList<>();
		User user = userDao.getUserByEmail(email);
		List<Ride> ridesFromDB=rideDao.getRideByUser(user);
		for (Ride ride : ridesFromDB) {
			rides.add(new com.gslab.parivahan.model.Ride(ride));
		}
		return rides;
		
	}

}
