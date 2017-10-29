package com.edylle.pathologicalreports.model.constant;

public final class NavIds {

	private static final NavIds instance = new NavIds();

	private final static String FORM = "nav-form";
	private final static String OWNERS = "nav-owners";
	private final static String USERS_ADMIN = "nav-users-admin";
	private final static String USERS_PROFESSOR = "nav-users-professor";
	private final static String USERS_STUDENT = "nav-users-student";
	private final static String ABOUT = "nav-about";

	private NavIds() {
	}

	// GETTERS
	public static NavIds getInstance() {
		return instance;
	}

	public String getForm() {
		return FORM;
	}

	public String getOwners() {
		return OWNERS;
	}

	public String getUsersAdmin() {
		return USERS_ADMIN;
	}

	public String getUsersProfessor() {
		return USERS_PROFESSOR;
	}

	public String getUsersStudent() {
		return USERS_STUDENT;
	}

	public static String getAbout() {
		return ABOUT;
	}

}
