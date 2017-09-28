package com.edylle.pathologicalreports.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = -922459359170477679L;

	@Id
	@Column(name = "USERNAME", length = 32)
	private String username;

	@Email
	@Column(name = "EMAIL", length = 128, unique = true, nullable = false)
	private String email;

	@Column(name = "PHONE_NUMBER", length = 16, nullable = false)
	private String phoneNumber;

	@Column(name = "PASSWORD", length = 80, nullable = false)
	private String password;

	@Column(name = "IMAGE_DIR", length = 255)
	private String imageDir;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATE_CREATED", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "ACTIVE", nullable = false)
	private Boolean active;

	@Cascade({ CascadeType.ALL })
	@ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "USERNAME_USER"))
	@Column(name = "ROLE", length = 16, nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<RoleEnum> roles;

	public User() {
		dateCreated = new Date();
		active = true;
	}

	// GETTERS AND SETTERS
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
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<RoleEnum> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEnum> roles) {
		this.roles = roles;
	}

}
