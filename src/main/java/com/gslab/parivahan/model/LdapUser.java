package com.gslab.parivahan.model;

import java.util.Collection;
import org.springframework.security.core.userdetails.User;

public class LdapUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;
	
	private UserVO userVO;
	
	private String email;

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public LdapUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection authorities,UserVO userVO,String email) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userVO=userVO;
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}