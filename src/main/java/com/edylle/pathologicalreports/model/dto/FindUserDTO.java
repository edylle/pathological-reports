package com.edylle.pathologicalreports.model.dto;

import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

public class FindUserDTO {

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
