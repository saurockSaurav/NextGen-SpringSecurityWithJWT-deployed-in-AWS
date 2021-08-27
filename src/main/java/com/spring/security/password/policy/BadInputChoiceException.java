package com.spring.security.password.policy;

public class BadInputChoiceException extends RuntimeException  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadInputChoiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
	
	public BadInputChoiceException(String errorMessage) {
        super(errorMessage);
    }

}
