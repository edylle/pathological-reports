package com.edylle.pathologicalreports.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
public class EmailProperties {

	private String username;
	private String password;
	private String smtpHost;
	private String smtpPort;
	private String applicationCtxPath;

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
