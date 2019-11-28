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
