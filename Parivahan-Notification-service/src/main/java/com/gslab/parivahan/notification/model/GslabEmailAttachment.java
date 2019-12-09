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
