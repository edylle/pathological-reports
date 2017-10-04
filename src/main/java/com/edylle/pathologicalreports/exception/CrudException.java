package com.edylle.pathologicalreports.exception;

import java.util.HashMap;
import java.util.Map;

public class CrudException extends RuntimeException {

	private static final long serialVersionUID = 3379806849614871065L;

	private Map<String, String> rejectedValues = new HashMap<>();

	public CrudException(Map<String, String> rejectedValues) {
		this.rejectedValues = rejectedValues;
	}

	// GETTERS AND SETTERS
	public Map<String, String> getRejectedValues() {
		return rejectedValues;
	}

	public void setRejectedValues(Map<String, String> rejectedValues) {
		this.rejectedValues = rejectedValues;
	}

}
