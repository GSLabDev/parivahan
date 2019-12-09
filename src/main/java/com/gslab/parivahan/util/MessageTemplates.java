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
package com.gslab.parivahan.util;

public class MessageTemplates {

	public final static String OFFERRIDE="Ride offered with Offer code %d. From: %s To : %s At %s";
	public final static String BOOKRIDEFORRIDER="Ride Requested with Booking code %d. From: %s To: %s At: %s. Host Contact: %s Cost: Rs %s.";
	public final static String BOOKRIDEFORHOST="New Ride Request for Offer code %d. From: %s To: %s At: %s Rider Contact: %s cost: Rs %s.";
	public final static String ACCEPTRIDEForRIDER="Request Accepted for Booking code %d. From: %s To : %s At: %s.Host Contact: %s Cost: Rs %s.";
	public final static String REJECTRIDEFORRIDER="Ride booking with Booking code %d. is Rejected. From: %s To : %s At: %s";
	public final static String REJECTRIDEFORHOST="You have rejected the ride request from: %s to %s on At: %s for Offer code %d.";
	public final static String ACCEPTRIDEFORHOST="Request Accepted for Offer code %d. From: %s To : %s At: %s.Rider Contact: %s Cost: Rs %s.";
	public final static String SUCCESSFULREGISTRATION="You have successfully registered on Parivahan";
	public final static String CANCELRIDEFORRIDER="Host has cancelled the ride for Booking code %d. From: %s To : %s At: %s.";
	public final static String CANCELRIDEFORHOST="You have cancelled ride with Offer code %d. From: %s To : %s At: %s.";
	public final static String CANCELBOOKINGFORRIDER="You have cancelled the booking with Booking code %d. From: %s To : %s At: %s";
	public final static String CANCELBOOKINGFORHOST="Booking is cancelled for Ride with Offer code %d. From: %s To : %s At: %s";
	
}
