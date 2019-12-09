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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

public class ParivahanRestTemplate<T> {

	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;
	
	public ParivahanRestTemplate() {
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Accept", "*/*");
	}

	public void setContentType(MediaType mediaType) {
		headers.setContentType(mediaType);
	}

	public void addHeader(String key, String value) {
		headers.add(key, value);
	}
	public ResponseEntity get(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(UriComponentsBuilder.fromUriString( uri).build().encode().toUri(), HttpMethod.GET, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity;
	}
	public ResponseEntity get(String uri, T entity) {
		HttpEntity<T> requestEntity = new HttpEntity<T>(entity, headers);
		ResponseEntity<String> responseEntity = rest.exchange( uri, HttpMethod.GET, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity;
	}

	public ResponseEntity post(String uri, T entity) {
		HttpEntity<T> request = new HttpEntity<T>(entity, headers);
		ResponseEntity<String> response = rest.postForEntity( uri, request, String.class);
		return response;
	}

	public ResponseEntity put(String uri, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange( uri, HttpMethod.PUT, requestEntity,
				String.class);
		return responseEntity;
	}

	public ResponseEntity delete(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange( uri, HttpMethod.DELETE, requestEntity,
				String.class);
		return responseEntity;
	}

	public ResponseEntity delete(String uri, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange( uri, HttpMethod.DELETE, requestEntity,
				String.class);
		return responseEntity;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
