package com.spring.security.jwt.util;

public interface UserLoginValidatable {

	void save(String userName, String password) throws Exception;

	boolean login(final String userName, final String passwordEnteredByUser) throws Exception;

}
