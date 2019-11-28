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
