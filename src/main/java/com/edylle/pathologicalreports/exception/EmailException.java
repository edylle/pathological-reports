package com.edylle.pathologicalreports.exception;

public class EmailException extends Exception {

	private static final long serialVersionUID = 7886778749366311533L;
	private static final String MESSAGE = "There was an error while sending the e-mail";

	public EmailException() {
		super(MESSAGE);
	}

}
