package com.gslab.parivahan.notification.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.gslab.parivahan.notification.dto.EmailDto;
import com.gslab.parivahan.notification.model.GslabEmail;
import com.gslab.parivahan.notification.service.EmailClient;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Component
public class EmailUtil {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private EmailClient emailClient;

	public HashMap<String, List<String>> sendEmailWithoutTemplate(String mailType, String templateName, List<String> to,
			List<String> cc, List<String> bcc, String body, String subject)
			throws CannotSendEmailException, IOException {
		HashMap<String, List<String>> map = new HashMap<>();
		HashMap<byte[], String> fileMap = new HashMap<>();
		List<String> invalidEmailIds = new ArrayList<>();
		List<String> validEmailIds = new ArrayList<>();
		GslabEmail email;
		if ((!cc.isEmpty()) || (!bcc.isEmpty()) || (!to.isEmpty())) {

			validateMailIds(cc, invalidEmailIds, validEmailIds);
			validateMailIds(bcc, invalidEmailIds, validEmailIds);
			validateMailIds(to, invalidEmailIds, validEmailIds);
			email = GslabEmail.builder().from(getSupportEmail()).body(body).to(to)
					.subject(subject).cc(cc).bcc(bcc).build();
			try {
				emailClient.sendEmailWithoutTemplating(email);
			} catch (AddressException e) {
				e.printStackTrace();
			}

		}
		map.put("validEmailIds", validEmailIds);
		map.put("invalidEmailIds", invalidEmailIds);
		return map;
	}

	public HashMap<String, List<String>> sendEmailWithTemplate(EmailDto dto)
			throws CannotSendEmailException, IOException {
		HashMap<String, List<String>> map = new HashMap<>();
		Map<String, Object> content = new HashMap<>();
		List<String> invalidEmailIds = new ArrayList<>();
		List<String> validEmailIds = new ArrayList<>();
		GslabEmail email;
		String template = null;
		if ((!dto.getCc().isEmpty()) || (!dto.getBcc().isEmpty()) || (!dto.getTo().isEmpty())) {
			validateMailIds(dto.getCc(), invalidEmailIds, validEmailIds);
			validateMailIds(dto.getBcc(), invalidEmailIds, validEmailIds);
			validateMailIds(dto.getTo(), invalidEmailIds, validEmailIds);
			if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.BOOK_RIDE_FOR_RIDER.toString())) {
				template = EmailTemplate.BOOK_RIDE_FOR_RIDER.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("bookingCode", dto.getBookingCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("cost", dto.getCost());
				content = data;
				// content =
				// ImmutableMap.of("name",dto.getHostName(),"bookingCode",dto.getBookingid(),"startLocation",dto.getStartLocation(),"endLocation",dto.getEndLocation(),"dateAndTime",dto.getDateAndTime().toString(),"cost",dto.getCost());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.BOOK_RIDE_FOR_HOST.toString())) {
				template = EmailTemplate.BOOK_RIDE_FOR_HOST.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("cost", dto.getCost());
				content = data;
				// content =
				// ImmutableMap.of("name",dto.getHostName(),"offerCode",dto.getOfferCode());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.OFFER_RIDE.toString())) {
				template = EmailTemplate.OFFER_RIDE.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("cost", dto.getCost());
				content = data;
				// content =
				// ImmutableMap.of("name",dto.getHostName(),"offerCode",dto.getOfferid());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.ACCEPT_RIDE_FOR_RIDER.toString())) {
				template = EmailTemplate.ACCEPT_RIDE_FOR_RIDER.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("bookingCode", dto.getBookingCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("cost", dto.getCost());
				content = data;
				// content =
				// ImmutableMap.of("name",dto.getHostName(),"bookingCode",dto.getBookingid());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.ACCEPT_RIDE_FOR_HOST.toString())) {
				template = EmailTemplate.ACCEPT_RIDE_FOR_HOST.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("cost", dto.getCost());
				data.put("availableSeats",dto.getAvailableSeats());
				content = data;
				// content =
				// ImmutableMap.of("name",dto.getHostName(),"offerCode",dto.getOfferCode());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.CANCEL_RIDE_FOR_HOST.toString())) {
				template = EmailTemplate.CANCEL_RIDE_FOR_HOST.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				content=data;
				//content = ImmutableMap.of("name", dto.getHostName(), "offerCode", dto.getOfferid());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.CANCEL_RIDE_FOR_RIDER.toString())) {
				template = EmailTemplate.CANCEL_RIDE_FOR_RIDER.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("bookingCode", dto.getBookingCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				content=data;
				//content = ImmutableMap.of("name", dto.getHostName(), "bookingCode", dto.getBookingid());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.REJECT_RIDE_FOR_HOST.toString())) {
				template = EmailTemplate.REJECT_RIDE_FOR_HOST.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("riderName", dto.getRiderName());
				data.put("availableSeats", dto.getAvailableSeats());
				content=data;
				//content = ImmutableMap.of("name", dto.getHostName(), "riderName", dto.getRiderName(), "offerCode",
					//	dto.getOfferid(),"availableSeats",dto.getAvailableSeats());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.REJECT_RIDE_FOR_RIDER.toString())) {
				template = EmailTemplate.REJECT_RIDE_FOR_RIDER.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("bookingCode", dto.getBookingCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime());
				content = data;
				//content = ImmutableMap.of("name", dto.getHostName(), "bookingCode", dto.getBookingid());
			} else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.REGISTERED_SUCCESSFULLY.toString())) {
				template = EmailTemplate.REGISTERED_SUCCESSFULLY.toString();
				content = ImmutableMap.of("name", dto.getHostName(), "userEmail", dto.getTo().get(0));
			}else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.CANCEL_BOOKING_FOR_HOST.toString())) {
				template = EmailTemplate.CANCEL_BOOKING_FOR_HOST.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("offerCode", dto.getOfferCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				data.put("availableSeats", dto.getAvailableSeats());
				content=data;
				//content = ImmutableMap.of("name", dto.getHostName(), "userEmail", dto.getTo().get(0));
			}else if (dto.getTemplateName().equalsIgnoreCase(EmailTemplate.CANCEL_BOOKING_FOR_RIDER.toString())) {
				template = EmailTemplate.CANCEL_BOOKING_FOR_RIDER.toString();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("name", dto.getHostName());
				data.put("bookingCode", dto.getBookingCode());
				data.put("startLocation", dto.getStartLocation());
				data.put("endLocation", dto.getEndLocation());
				data.put("dateAndTime", dto.getDateAndTime().toString());
				content = data;
				//content = ImmutableMap.of("name", dto.getHostName(), "bookingCode", dto.getBookingCode());
			}
			HashMap<byte[], String> fileMap = new HashMap<>();
			if (null != dto.getInvite())
				fileMap.put(dto.getInvite(), dto.getTo().get(0) + ".ics");
			if (fileMap.isEmpty()) {
				email = GslabEmail.builder().from(getSupportEmail()).body(dto.getBody()).to(dto.getTo())
						.subject(dto.getSubject()).cc(dto.getCc()).bcc(dto.getBcc()).template(template)
						.templateContent(content).build();
			} else {
				email = GslabEmail.builder().from(getSupportEmail()).body(dto.getBody()).to(dto.getTo())
						.subject(dto.getSubject()).cc(dto.getCc()).bcc(dto.getBcc()).template(template)
						.templateContent(content).fileMap(fileMap).build();
			}
			try {
				emailClient.sendMimeEmailWithTemplateAndInlineImage(email);
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
		map.put("validEmailIds", validEmailIds);
		map.put("invalidEmailIds", invalidEmailIds);
		return map;
	}

	private void validateMailIds(List<String> mailIds, List<String> invalidEmailIds, List<String> validEmailIds) {
		if (!mailIds.isEmpty()) {
			Iterator<String> it = mailIds.iterator();

			while (it.hasNext()) {
				String strElement = (String) it.next();
				if (!EmailValidator.validateEmail(strElement)) {
					invalidEmailIds.add(strElement);
					it.remove();
				} else {
					validEmailIds.add(strElement);
				}
			}
		}
		
	}
	
	public String getSupportEmail() {
		return env.getProperty("gslab.support.email");
}
}
