package com.edylle.pathologicalreports.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.security.LoggedUser;

public class UserUtils {

	public static User getUser() {
		return ((LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}

	public static boolean isUserLogged() {
		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}

}
