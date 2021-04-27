package com.spring.security.jwt.pojo;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String path;
	private String status;

	public ExceptionResponse(Date timestamp, String message, String details, String status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.path = details;
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

	public String getPath() {
		return path;
	}

}
