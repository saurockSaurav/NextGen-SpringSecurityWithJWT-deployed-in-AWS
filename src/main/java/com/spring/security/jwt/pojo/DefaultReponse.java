package com.spring.security.jwt.pojo;

import java.util.Date;

public class DefaultReponse {

	private Date timestamp;
	private String message;
	private String status;

	public DefaultReponse(Date timestamp, String message, String status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

}
