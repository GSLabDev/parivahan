package com.gslab.parivahan.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gslab.parivahan.model.ParivahanUserContext;
import com.gslab.parivahan.model.UserVO;
import com.gslab.parivahan.service.IUserService;

import io.swagger.annotations.ApiParam;

@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(RidesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request; 
    @Autowired
    private IUserService userService;
    
    @Autowired
    private Environment env;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
	@Override
	public ResponseEntity<List<UserVO>> getAllUsers(
			@ApiParam(value = "name", required = false, defaultValue = "") @RequestParam(value = "name", required = false) String name,
			@ApiParam(value = "username", required = false, defaultValue = "") @RequestParam(value = "username", required = false) String username) {
		List<UserVO> users = new ArrayList<UserVO>();
		
		
		String email = "";
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
			ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal());
		 UserVO user = userService.getUserByUsername(usercontext.getUsername());
		if(null != user) {
			email = user.getEmail();
			if (StringUtils.isBlank(email))
				return null;
		}
		}
		
		if (StringUtils.isBlank(email) && StringUtils.isBlank(name) && StringUtils.isBlank(username)) {
			users = userService.getAllUsers();
		} else if (StringUtils.isBlank(name) && StringUtils.isBlank(username)) {
			UserVO userVO = userService.getUserByEmail(email);
			if (userVO != null) {
				users.add(userVO);
			}
		} else if (StringUtils.isBlank(username) && StringUtils.isBlank(email)) {
			UserVO userVO = userService.getUserByName(name);
			if (userVO != null) {
				users.add(userVO);
			}
		} else {
			UserVO userVO = userService.getUserByUsername(username);
			if (userVO != null) {
				users.add(userVO);
			}
		}
		return new ResponseEntity<List<UserVO>>(users, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<UserVO> addUsers(@RequestBody UserVO userVO) {
		if (Boolean.parseBoolean(env.getRequiredProperty("parivahan.auth.ldap.enabled"))) {
		ParivahanUserContext usercontext = ((ParivahanUserContext) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		userVO.setUsername(usercontext.getUsername());
		}
	    userVO=userService.addUser(userVO);
		return new ResponseEntity<UserVO>(userVO,HttpStatus.OK);
	}

}