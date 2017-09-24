package com.edylle.pathologicalreports.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edylle.pathologicalreports.model.entity.User;

public class LoggedUser extends org.springframework.security.core.userdetails.User implements UserDetails {

	private static final long serialVersionUID = 3021171609299255196L;

	private final User user;

	public LoggedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
		super(username, password, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
