package com.edylle.pathologicalreports.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

public class UserVO implements Serializable {

	private static final long serialVersionUID = -5151744727802162300L;

	private String formerUsername;

	@NotEmpty(message = "{validation.field.required.username}")
	@Size(max = 32, message = "{validation.length.username}")
	private String username;

	@Email(message = "{validation.email}")
	@NotEmpty(message = "{validation.field.required.email}")
	@Size(max = 128, message = "{validation.length.email}")
	private String email;

	@NotEmpty(message = "{validation.field.required.phonenumber}")
	@Size(max = 16, message = "{validation.length.phonenumber}")
	private String phoneNumber;

	private String password;
	private String confirmPassword;
	private String imageDir;

	@NotNull(message = "{validation.field.required.role}")
	private RoleEnum role;

	public UserVO() {

	}

	public UserVO(User user) {
		formerUsername = user.getUsername();
		username = user.getUsername();
		email = user.getEmail();
		phoneNumber = user.getPhoneNumber();
		imageDir = user.getImageDir();
		role = user.getRoles().iterator().next(); // this project has only on role per user
	}

	// GETTERS AND SETTERSFs
	public String getFormerUsername() {
		return formerUsername;
	}

	public void setFormerUsername(String formerUsername) {
		this.formerUsername = formerUsername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

}
