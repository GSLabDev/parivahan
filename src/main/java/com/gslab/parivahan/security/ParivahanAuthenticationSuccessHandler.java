package com.gslab.parivahan.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.model.ParivahanUserContext;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */
@Component
public class ParivahanAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

	private final ObjectMapper mapper;
	private final ParivahanJwtTokenFactory tokenFactory;
	
	
	public ParivahanAuthenticationSuccessHandler(final ObjectMapper mapper,final ParivahanJwtTokenFactory tokenFactory ) {
		this.mapper = mapper;
		this.tokenFactory = tokenFactory;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		ParivahanUserContext userContext = (ParivahanUserContext)authentication.getPrincipal();
		IParivahanJwtToken accessToken = tokenFactory.createAcccessToken(userContext);
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("token", accessToken.getToken());
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mapper.writeValue(response.getWriter(), tokenMap);
		
		clearAuthenticationAttributes(request);
	}

	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
