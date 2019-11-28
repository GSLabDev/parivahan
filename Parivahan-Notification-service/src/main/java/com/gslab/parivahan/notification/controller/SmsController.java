package com.gslab.parivahan.notification.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gslab.parivahan.notification.dto.SMSDto;
import com.gslab.parivahan.notification.service.SendSmsService;
import com.gslab.parivahan.notification.util.Response;

@RestController
public class SmsController {
	
	@Autowired
	private SendSmsService smsService;
	
	@Autowired
	private Environment env;

	private final String ENDPOINT = "/sms"; 
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Object>> sendEmail(HttpServletRequest request,@RequestBody SMSDto dto) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		HttpStatus httpStatus;
		String data = null;
		Response<Object> response = new Response<>("", map);
		try {
			if(Boolean.parseBoolean(env.getProperty("gslab.sms.enable"))){
				data = smsService.sendSms(dto);
				response.setData(data);
				httpStatus = HttpStatus.OK;
			}else {
				response.setData("SMS service is disabled");
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			e.getStackTrace();
			httpStatus=HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response<Object>>(response, httpStatus);
	}
}
