package com.gslab.parivahan.notification.controller;

import java.io.IOException;
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

import com.gslab.parivahan.notification.dto.EmailDto;
import com.gslab.parivahan.notification.util.EmailUtil;
import com.gslab.parivahan.notification.util.Response;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@RestController
public class EmailController {

private final String ENDPOINT = "/email";
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private Environment env;
	 
	@RequestMapping(value = ENDPOINT+"/template", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Object>> sendEmail(@RequestBody EmailDto emailDto) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		HttpStatus httpStatus;
		Response<Object> response = new Response<>("", map);
		HashMap<String, List<String>> emailMap = new HashMap<String, List<String>>();
		try {
			if(Boolean.parseBoolean(env.getProperty("gslab.email.enable"))) {
				emailMap = emailUtil.sendEmailWithTemplate(emailDto);

				if (!(emailMap.get("invalidEmailIds").isEmpty())) {
					map.put("invalidMailIds", emailMap.get("invalidEmailIds"));
				}
				if (!(emailMap.get("validEmailIds").isEmpty())) {
					map.put("validMailIds", emailMap.get("validEmailIds"));
				}
				httpStatus = HttpStatus.OK;
				response.setMessage("An email has been sent.");
				response.setData(map);
			}else {
				httpStatus = HttpStatus.OK;
				response.setMessage("Email service is disabled.");
				response.setData(map);
			}
			
		} catch (CannotSendEmailException exception) {
			response.setMessage("Could not send email");
			response.setData(map);
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (IOException exception) {
			response.setMessage("Could not send email, please check the attachments!");
			response.setData(map);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response<Object>>(response, httpStatus);
	}
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Object>> sendEmailWithoutTemplate(HttpServletRequest request, EmailDto emailDto) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		HttpStatus httpStatus;
		Response<Object> response = new Response<>("", map);
		try {
			
			HashMap<String, List<String>> emailMap = emailUtil.sendEmailWithoutTemplate(emailDto.getMailType(),
					emailDto.getTemplateName(), emailDto.getTo(), emailDto.getCc(), emailDto.getBcc(),
					emailDto.getBody(), emailDto.getSubject());

			if (!(emailMap.get("invalidEmailIds").isEmpty())) {
				map.put("invalidMailIds", emailMap.get("invalidEmailIds"));
			}
			if (!(emailMap.get("validEmailIds").isEmpty())) {
				map.put("validMailIds", emailMap.get("validEmailIds"));
			}
			httpStatus = HttpStatus.OK;
			response.setMessage("An email has been sent.");
			response.setData(map);
		} catch (CannotSendEmailException exception) {
			response.setMessage("Could not send email");
			response.setData(map);
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (IOException exception) {
			response.setMessage("Could not send email, please check the attachments!");
			response.setData(map);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response<Object>>(response, httpStatus);
	}
	
}
