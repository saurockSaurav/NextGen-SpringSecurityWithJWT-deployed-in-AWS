package com.spring.security.jwt.util;

import java.io.IOException;

public interface UserLoginValidatable {

	void save(String userName, String password) throws IOException;

	boolean login(final String userName, final String passwordEnteredByUser) throws IOException;

}
