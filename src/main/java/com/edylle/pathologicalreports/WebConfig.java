package com.edylle.pathologicalreports;

import java.util.EnumSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.springframework.web.WebApplicationInitializer;

public class WebConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
	}

}
