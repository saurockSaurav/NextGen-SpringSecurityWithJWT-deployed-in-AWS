package com.spring.security.jwt.pojo;

import java.util.Date;

public class PasswordRecoverResponse {

	private Date timestamp;
	private String password;

	public PasswordRecoverResponse(Date timestamp, String password) {
		super();
		this.timestamp = timestamp;
		this.password = password;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getPassword() {
		return password;
	}

}
