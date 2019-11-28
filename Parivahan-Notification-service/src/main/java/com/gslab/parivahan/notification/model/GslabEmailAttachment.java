package com.gslab.parivahan.notification.model;

import java.io.IOException;

import org.springframework.http.MediaType;

import it.ozimov.springboot.mail.model.EmailAttachment;

public class GslabEmailAttachment implements EmailAttachment{

	private static final long serialVersionUID = 1L;

	private byte[] bytes;
	private String filename;

	
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	@Override
	public String getAttachmentName() {
		return filename;
	}

	@Override
	public byte[] getAttachmentData() {
		return bytes;
	}

	@Override
	public MediaType getContentType() throws IOException {

		return MediaType.ALL;
	}
}
