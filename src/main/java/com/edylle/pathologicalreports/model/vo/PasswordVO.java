package com.edylle.pathologicalreports.model.vo;

import java.io.Serializable;

public class PasswordVO implements Serializable {

	private static final long serialVersionUID = 6503538353456790732L;

	private String password;
	private String confirmPassword;

	// GETTERS AND SETTERS
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
