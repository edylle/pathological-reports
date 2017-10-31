package com.edylle.pathologicalreports.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 378245765176312513L;

	private boolean isNewRegistry;

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
	private String imagePath;

	@NotNull(message = "{validation.field.required.role}")
	private RoleEnum role;

	private Boolean active;

	public UserVO() {
		isNewRegistry = true;
	}

	public UserVO(boolean isNewRegistry) {
		this.isNewRegistry = isNewRegistry;
	}

	public UserVO(boolean isNewRegistry, RoleEnum role) {
		this.isNewRegistry = isNewRegistry;
		this.role = role;
	}

	public UserVO(User user, boolean isNewRegistry) {
		this.isNewRegistry = isNewRegistry;
		username = user.getUsername();
		email = user.getEmail();
		phoneNumber = user.getPhoneNumber();
		imagePath = user.getImagePath();
		active = user.getActive();
		role = user.getRoles().iterator().next(); // this project has only on role per user
	}

	// GETTERS AND SETTERSFs
	public boolean isNewRegistry() {
		return isNewRegistry;
	}

	public void setNewRegistry(boolean isNewRegistry) {
		this.isNewRegistry = isNewRegistry;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
