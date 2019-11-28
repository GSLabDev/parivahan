package com.gslab.parivahan.notification.util;

public enum EmailTemplate {

	BOOK_RIDE_FOR_HOST("email/bookRideForHost"), BOOK_RIDE_FOR_RIDER("email/bookRideForRider"),
	OFFER_RIDE("email/offerRide"), ACCEPT_RIDE_FOR_HOST("email/acceptRideForHost"),
	ACCEPT_RIDE_FOR_RIDER("email/acceptRideForRider"), CANCEL_RIDE_FOR_HOST("email/cancelRideForHost"),
	CANCEL_RIDE_FOR_RIDER("email/cancelRideForRider"), REJECT_RIDE_FOR_HOST("email/rejectRideToHost"),
	REJECT_RIDE_FOR_RIDER("email/rejectRideToRider"), REGISTERED_SUCCESSFULLY("email/registrationSuccess"),
	CANCEL_BOOKING_FOR_HOST("email/cancelBookingForHost"),CANCEL_BOOKING_FOR_RIDER("email/cancelBookingForRider");

	private final String templatePath;

	private EmailTemplate(String path) {
		templatePath = path;
	}

	public String toString() {
		return this.templatePath;
	}

}
