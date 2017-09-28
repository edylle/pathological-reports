package com.edylle.pathologicalreports.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edylle.pathologicalreports.utils.TokenUtils;

@Entity
@Table(name = "RECOVER_PASSWORD")
public class RecoverPassword implements Serializable {

	private static final long serialVersionUID = 3394409959926602876L;

	@Id
	@Column(name = "TOKEN", length = 32)
	private String token;

	@ManyToOne
	@JoinColumn(name = "USERNAME_USER", nullable = false)
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_REQUESTED", nullable = false)
	private Date dateRequested;

	public RecoverPassword() {

	}

	public RecoverPassword(User user) {
		this.token = TokenUtils.generateTokenOf(32); // same size as the token Id column length
		this.user = user;
		dateRequested = new Date();
	}

	// GETTERS AND SETTERS
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDateRequested() {
		return dateRequested;
	}

	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

}
