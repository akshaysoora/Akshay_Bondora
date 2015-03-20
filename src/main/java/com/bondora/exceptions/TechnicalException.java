package com.bondora.exceptions;

public class TechnicalException extends Exception {

	private static final long serialVersionUID = 1L;

	public TechnicalException() {
		super();
	}

	public TechnicalException(String message) {
		super(message);
	}

	
	public String getMessage() {
		return super.getMessage();
	}

}