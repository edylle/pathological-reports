package com.edylle.pathologicalreports.exception;

public class TokenException extends Exception {

	private static final long serialVersionUID = -2064760020080344575L;
	private static final String MESSAGE = "Invalid token";

	public TokenException() {
		super(MESSAGE);
	}

}
