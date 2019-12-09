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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gslab.parivahan.model.EmailMessageBody;
import com.gslab.parivahan.model.SmsMessageBody;
import com.gslab.parivahan.util.ParivahanRestTemplate;

@Service
public class NotificationService {

	@Autowired
	private Environment env;
	
	@Async
	public String sendSms(String mobileNumber,String message) {
		SmsMessageBody messageBody =  new SmsMessageBody(mobileNumber, message);
		ParivahanRestTemplate<Object> restTemplate = new ParivahanRestTemplate<Object>();
		restTemplate.setContentType(MediaType.APPLICATION_JSON);
		String uri = env.getProperty("parivahan.notification.url")+"/sms";
	    ResponseEntity result = restTemplate.post(uri, messageBody);
	    return result.getBody().toString();
	}
	
	@Async
	public String sendEmail(EmailMessageBody messageBody) {
		ParivahanRestTemplate<Object> restTemplate = new ParivahanRestTemplate<Object>();
		restTemplate.setContentType(MediaType.APPLICATION_JSON);
		String uri = env.getProperty("parivahan.notification.url")+"/email/template";
	    ResponseEntity result = restTemplate.post(uri, messageBody);
	    return result.getBody().toString();
	}
}
