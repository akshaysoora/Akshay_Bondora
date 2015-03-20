package com.bondora.exceptions;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;


	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	
	public String getMessage() {
		return super.getMessage();
	}

}