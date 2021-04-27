package com.spring.security.jwt.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Component
public class WebLoginControllerAssert {

	protected void preValidation(final String emailAddress) throws BadRequest {
		Assert.hasLength(emailAddress, "Username cannot be null or empty");
		Assert.isTrue(isValidEmailAddress(emailAddress), "Invalid Email address.");
	}

	protected void preValidation(final String emailAddress, final String password) throws BadRequest {
		Assert.hasLength(emailAddress, "Username cannot be null or empty");
		Assert.isTrue(isValidEmailAddress(emailAddress), "Invalid Email Address");
		Assert.hasLength(password, "Password cannot be null or empty");
		Assert.isTrue(isValidPassword(password), "Password didnot meet Password Policy and Guidelines. It must be mix of :  ^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
		}

	private static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	
	public static boolean isValidPassword(String password) {
		boolean isValid = true;
		if (password.length() > 15 || password.length() < 8) {
			isValid = false;
		}
		String upperCaseChars = "(.*[A-Z].*)";
		if (!password.matches(upperCaseChars)) {
			isValid = false;
		}
		String lowerCaseChars = "(.*[a-z].*)";
		if (!password.matches(lowerCaseChars)) {
			isValid = false;
		}
		String numbers = "(.*[0-9].*)";
		if (!password.matches(numbers)) {
			isValid = false;
		}
		String specialChars = "(.*[@,#,$,%].*$)";
		if (!password.matches(specialChars)) {
			isValid = false;
		}
		return isValid;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private static boolean isValidPassword(String password, Object obj) {

		final String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
