package com.edylle.pathologicalreports.model.dto;

import java.io.Serializable;

import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

public class FindUserDTO implements Serializable {

	private static final long serialVersionUID = 976657933187759765L;

	private RoleEnum role;
	private String usernameOrEmail;

	// GETTERS AND SETTERS
	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

}
