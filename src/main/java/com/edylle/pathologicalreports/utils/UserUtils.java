package com.edylle.pathologicalreports.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.security.LoggedUser;

public class UserUtils {

	public static User getUser() {
		return ((LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	public static boolean isUserLogged() {
		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}

	public static boolean isAdmin() {
		if (!isUserLogged())
			return false;

		return (getUser().getRoles().contains(RoleEnum.ADMIN));
	}

	public static boolean isProfessor() {
		if (!isUserLogged())
			return false;

		return (getUser().getRoles().contains(RoleEnum.PROFESSOR));
	}

	public static boolean isStudent() {
		if (!isUserLogged())
			return false;

		return (getUser().getRoles().contains(RoleEnum.STUDENT));
	}

}
