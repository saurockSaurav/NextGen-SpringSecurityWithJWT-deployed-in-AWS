package com.spring.security.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "nextgen.jwt")
@Configuration
@EnableConfigurationProperties
public class YmlConfig {

	private String propFilePath;
	private String username;
	private String password;

	public YmlConfig(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getPropFilePath() {
		return propFilePath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// need default constructor for JSON Parsing
	public YmlConfig() {

	}

	public void setPropFilePath(String propFilePath) {
		this.propFilePath = propFilePath;
	}
}
