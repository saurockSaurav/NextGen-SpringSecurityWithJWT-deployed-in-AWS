package com.spring.security.jwt.controller;

import javax.mail.internet.AddressException;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.spring.security.password.policy.BadInputChoiceException;
import com.spring.security.password.policy.PasswordRules;

public class WebLoginControllerAssert extends PasswordRules {

	public boolean validateUnameAndPwd (final String userName, final String password, boolean ignorePwdPolicy) throws BadInputChoiceException, AddressException {

		if (ignorePwdPolicy) {
			return StringUtils.hasText(userName) && isValidEmailAddress(userName);
		} else {
			return StringUtils.hasText(userName) && isValidEmailAddress(userName)
												 && StringUtils.hasText(password) 
												 && isValidPassword(password)
												 && doesPasswordMeetsStandard(password);
		}
	}

	
	protected void preValidation(final String emailAddress) throws BadInputChoiceException, AddressException {

		Assert.hasLength(emailAddress, "Username cannot be null or empty");
		Assert.isTrue(super.isValidEmailAddress(emailAddress), "Invalid Email address.");

	}

	protected void validateUnameAndPwd(final String emailAddress, final String password) throws BadInputChoiceException, AddressException {

		Assert.hasLength(emailAddress, "Username cannot be null or empty");

		Assert.isTrue(isValidEmailAddress(emailAddress), "Invalid Email Address");

		Assert.hasLength(password, "Password cannot be null or empty");

		Assert.isTrue(isValidPassword(password), "Password didnot meet Password Policy and Guidelines. It must be mix of :  ^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
	}

}
