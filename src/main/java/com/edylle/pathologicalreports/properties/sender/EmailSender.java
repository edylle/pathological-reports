package com.edylle.pathologicalreports.properties.sender;

import com.edylle.pathologicalreports.properties.EmailProperties;

public class EmailSender {

	private String username;
	private String password;
	private String smtpHost;
	private String smtpPort;
	private String applicationCtxPath;

	public EmailSender() {

	}

	public EmailSender(EmailProperties properties) {
		username = properties.getUsername();
		password = properties.getPassword();
		smtpHost = properties.getSmtpHost();
		smtpPort = properties.getSmtpPort();
		applicationCtxPath = properties.getApplicationCtxPath();
	}

	// GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getApplicationCtxPath() {
		return applicationCtxPath;
	}

	public void setApplicationCtxPath(String applicationCtxPath) {
		this.applicationCtxPath = applicationCtxPath;
	}

}
