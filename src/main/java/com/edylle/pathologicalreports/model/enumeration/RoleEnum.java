package com.edylle.pathologicalreports.model.enumeration;

public enum RoleEnum {

	ADMIN("Administrator", "/admin/home"),
	PROFESSOR("Professor", "/professor/home"),
	STUDENT("Student", "/student/home");

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
		return description;
	}

	public String getHomeUrl() {
		return homeUrl;
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
