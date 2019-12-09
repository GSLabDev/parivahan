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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	public static final DateFormat DATE_FORMATTER_DAY_MONTH = new SimpleDateFormat("EEEE,dd MMMM hh:mm a");
	public static final SimpleDateFormat DATE_FORMATTER_DATE_AND_TIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
	public static final SimpleDateFormat DATE_FORMATTER_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DATE_FORMATTER_FOR_INVITATION = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	public static String ConvertDateToUserReadableFormat(Date date) {
		return DATE_FORMATTER_DAY_MONTH.format(date);
	}


	/**
	 * Parses date and time string and returns a Date object 
	 * @param sdf
	 * @param dateString
	 * @return Date object
	 */
	public static Date parseDate(SimpleDateFormat sdf, String dateString) {
		Date date = null;
		try {
			if(!dateString.isEmpty()) {
					date = sdf.parse(dateString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log.info("parseDate("+dateString+")= "+date);
		return date;
	}


	/**
	 * @param date
	 * @param noOfDays
	 * @return
	 */
	public static Date addDays(Date currentDate, int noOfDays) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(currentDate);
		calender.add(calender.DATE, noOfDays);
		return calender.getTime();
	}
	
	public static Date addHours(Date date, int noOfHours) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.HOUR,noOfHours);
		return calender.getTime();
	}
	
	public static Date removeTimestampFromDate(Date searchLimitDate) throws ParseException {
		return DATE_FORMATTER_DATE.parse(DATE_FORMATTER_DATE.format(searchLimitDate));
	}
	
	public static String ConvertDateToInvitationFormat(Date date) {
		DATE_FORMATTER_FOR_INVITATION.setTimeZone(TimeZone.getTimeZone("GMT"));
		return DATE_FORMATTER_FOR_INVITATION.format(date);
	}
}
