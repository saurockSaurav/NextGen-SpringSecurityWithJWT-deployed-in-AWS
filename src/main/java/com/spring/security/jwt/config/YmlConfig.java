package com.spring.security.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "nextgen.jwt")
@Configuration
@EnableConfigurationProperties
public class YmlConfig {

	String propFilePath;

	public String getPropFilePath() {
		return propFilePath;
	}

	public void setPropFilePath(String propFilePath) {
		this.propFilePath = propFilePath;
	}
}
