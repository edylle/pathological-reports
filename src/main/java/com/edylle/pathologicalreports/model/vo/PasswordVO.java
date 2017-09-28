package com.edylle.pathologicalreports.model.vo;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordVO implements Serializable {

	private static final long serialVersionUID = 6503538353456790732L;

	@NotEmpty(message = "{validation.field.required.password}")
	@Size(min = 5, max = 16, message = "{validation.length.password}")
	private String password;

	@NotEmpty(message = "{validation.field.required.confirm.password}")
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
