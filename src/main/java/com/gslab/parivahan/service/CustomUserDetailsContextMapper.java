package com.gslab.parivahan.service;

import java.util.Collection;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.gslab.parivahan.model.LdapUser;
import com.gslab.parivahan.model.UserVO;

@Component
public class CustomUserDetailsContextMapper implements UserDetailsContextMapper{
	@Autowired
	IUserService userService;

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		String email = null;
		try {
			email = (String) ctx.getAttributes().get("mail").get(0);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		UserVO user = userService.getUserByEmail(email);
		return new LdapUser(username, "", true, true, true, true, authorities,user,email);
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		// TODO Auto-generated method stub
		
	}

}
