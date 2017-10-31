package com.edylle.pathologicalreports.model.enumeration;

import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edylle.pathologicalreports.utils.Messages;

public enum RoleEnum {

	ADMIN("role.description.admin", "/admin/list-users"),
	PROFESSOR("role.description.professor", "/professor/list-users"),
	STUDENT("role.description.student", "/student/my-account");

	@Component
	public static class EnumServiceInjector {
		@Autowired
		private Messages messages;

		@PostConstruct
		public void postConstruct() {
			for (RoleEnum role : EnumSet.allOf(RoleEnum.class))
				role.setMessages(messages);
		}
	}

	private Messages messages;

	private String springSecurityRole;
	private String description;
	private String homeUrl;

	RoleEnum(String description, String homeUrl) {
		springSecurityRole = "ROLE_".concat(this.name());
		this.description = description;
		this.homeUrl = homeUrl;
	}

	// GETTERS AND SETTERS
	public String getSpringSecurityRole() {
		return springSecurityRole;
	}

	public String getDescription() {
		return messages.getMessageBy(description);
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public static RoleEnum getSpringRoleBy(String springRole) {
		for (RoleEnum roleEnum : RoleEnum.values()) {
			if ("ROLE_".concat(roleEnum.name()).equals(springRole)) {
				return roleEnum;
			}
		}

		return null;
	}

}
