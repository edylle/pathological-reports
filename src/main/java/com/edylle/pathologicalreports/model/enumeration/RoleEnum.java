package com.edylle.pathologicalreports.model.enumeration;

import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edylle.pathologicalreports.utils.Messages;

public enum RoleEnum {

	ADMIN("role.admin.description", "/admin/find-users"),
	PROFESSOR("role.professor.description", "/professor/home"),
	STUDENT("role.student.description", "/student/home");

	@Component
	public static class RoleEnumServiceInjector {
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
