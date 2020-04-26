package com.cmpe275.lab2.errors;

public class AlreadyExistsException extends RuntimeException{

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
