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
package com.gslab.parivahan.notification.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gslab.parivahan.notification.model.GslabEmail;
import com.gslab.parivahan.notification.model.GslabEmailAttachment;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.EmailAttachment;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Component
public class EmailClient {

	@Autowired
	private EmailService emailService;
	

	public void sendEmailWithoutTemplating(GslabEmail gslabEmail) throws UnsupportedEncodingException, AddressException {
		InternetAddress[] toAddress  = new  InternetAddress[gslabEmail.getTo().size()];
		for (int i = 0; i < gslabEmail.getTo().size(); i++) {
			toAddress[i] = new InternetAddress(gslabEmail.getTo().get(i));
		}
		final Email email = DefaultEmail.builder().from(new InternetAddress(gslabEmail.getFrom()))
				.to(Lists.newArrayList(toAddress)).subject(gslabEmail.getSubject())
				.body(gslabEmail.getBody() == null ? "" : gslabEmail.getBody()).encoding("UTF-8").build();
		emailService.send(email);
	}
	
	public void sendMimeEmailWithTemplate(GslabEmail gslabEmail) throws UnsupportedEncodingException, AddressException, CannotSendEmailException {
		InternetAddress[] toAddress  = new  InternetAddress[gslabEmail.getTo().size()];
		for (int i = 0; i < gslabEmail.getTo().size(); i++) {
			toAddress[i] = new InternetAddress(gslabEmail.getTo().get(i));
		}
		final Email email = DefaultEmail.builder().from(new InternetAddress(gslabEmail.getFrom()))
				.to(Lists.newArrayList(toAddress)).subject(gslabEmail.getSubject())
				.body(gslabEmail.getBody() == null ? "" : gslabEmail.getBody()).encoding("UTF-8").build();
		emailService.send(email,gslabEmail.getTemplate(),gslabEmail.getTemplateContent());
	}

	public void sendMimeEmailWithTemplateAndInlineImage(GslabEmail gslabEmail) throws AddressException, CannotSendEmailException {

		Email email = null;
		InternetAddress[] bccAddress = new  InternetAddress[gslabEmail.getBcc().size()];
		InternetAddress[] ccAddress = new  InternetAddress[gslabEmail.getCc().size()];
		InternetAddress[] toAddress  = new  InternetAddress[gslabEmail.getTo().size()];

		for (int i = 0; i < gslabEmail.getTo().size(); i++) {
			toAddress[i] = new InternetAddress(gslabEmail.getTo().get(i));
		}
		if(!gslabEmail.getCc().isEmpty())  {
			for (int i = 0; i < gslabEmail.getCc().size(); i++) {
				ccAddress[i] = new InternetAddress(gslabEmail.getCc().get(i));
			}
		}
		if(!gslabEmail.getBcc().isEmpty())  {
			for (int i = 0; i < gslabEmail.getBcc().size(); i++) {
				bccAddress[i] = new InternetAddress(gslabEmail.getBcc().get(i));
			}
		}
		
		if(!((gslabEmail.getFileMap()).isEmpty())) {
			email =	emailWithAttachment(email, gslabEmail, toAddress, bccAddress, ccAddress);
		} else {
			email = DefaultEmail.builder().from(new InternetAddress(gslabEmail.getFrom()))
					.to(Lists.newArrayList(toAddress)).subject(gslabEmail.getSubject())
					.cc(Lists.newArrayList(ccAddress))
					.bcc(Lists.newArrayList(bccAddress))
					.body(gslabEmail.getBody() == null ? " " : gslabEmail.getBody()).encoding("UTF-8").build();
		}
			if(!StringUtils.isBlank(gslabEmail.getTemplate())) {
					String template = gslabEmail.getTemplate().toString();
					emailService.send(email, template, gslabEmail.getTemplateContent());
			}
			
	}
	
	private Email emailWithAttachment(Email email, GslabEmail gslabEmail, InternetAddress[] toAddress, InternetAddress[] bccAddress,
			InternetAddress[] ccAddress) throws AddressException {
		List<EmailAttachment> attachment = new ArrayList<>();
		for(Map.Entry<byte[], String> entry : gslabEmail.getFileMap().entrySet()) {
			GslabEmailAttachment attach = new GslabEmailAttachment();
			attach.setBytes(entry.getKey());
			attach.setFilename(entry.getValue());
			attachment.add(attach);
		}
		
		return email = DefaultEmail.builder().from(new InternetAddress(gslabEmail.getFrom()))
				.to(Lists.newArrayList(toAddress)).subject(gslabEmail.getSubject())
				.cc(Lists.newArrayList(ccAddress))
				.bcc(Lists.newArrayList(bccAddress))
				.attachments(attachment)
				.body(gslabEmail.getBody() == null ? "" : gslabEmail.getBody()).encoding("UTF-8").build();
	}

}
