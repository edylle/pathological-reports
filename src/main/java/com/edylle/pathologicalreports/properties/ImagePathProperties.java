package com.edylle.pathologicalreports.properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public class ImagePathProperties {

	@Autowired
	private Environment environment;

	private String usersPath;
	private String formsPath;

	private String userContext;
	private String formContext;

	@PostConstruct
	public void init() {

		if (SystemUtils.IS_OS_LINUX) {
			usersPath = environment.getProperty("image.path.linux.users");
			formsPath = environment.getProperty("image.path.linux.forms");

		} else if (SystemUtils.IS_OS_MAC) {
			usersPath = environment.getProperty("image.path.mac.users");
			formsPath = environment.getProperty("image.path.mac.forms");

		} else if (SystemUtils.IS_OS_WINDOWS) {
			usersPath = environment.getProperty("image.path.windows.users");
			formsPath = environment.getProperty("image.path.windows.forms");
		}

		userContext = environment.getProperty("image.context.user");
		formContext = environment.getProperty("image.context.form");
	}

	// GETTERS
	public String getUsersPath() {
		return usersPath;
	}

	public String getFormsPath() {
		return formsPath;
	}

	public String getUserContext() {
		return userContext;
	}

	public String getFormContext() {
		return formContext;
	}

}
