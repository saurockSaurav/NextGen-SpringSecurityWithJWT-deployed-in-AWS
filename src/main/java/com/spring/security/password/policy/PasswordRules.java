package com.spring.security.password.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;


public class PasswordRules {
	
	protected static boolean isValidPassword(String password) {
		
	
		if (password.length() > 15 || password.length() < 8) {
			throw new BadInputChoiceException("Password doesnot meet length. Must be between 8 to 15");
		}
		if (!password.matches("(.*[A-Z].*)")) {
			throw new BadInputChoiceException("Password must have atleast one UpperCase character");
		}
		if (!password.matches("(.*[a-z].*)")) {
			throw new BadInputChoiceException("Password  must have atleast one LowerCase character");
		}
		if (!password.matches("(.*[0-9].*)")) {
			throw new BadInputChoiceException("Password must have atleast one numeric character");
		}
		String specialChars = "(.*[@,#,$,%].*$)";
		if (!password.matches(specialChars)) {
			throw new BadInputChoiceException("Password must have atleast one special character"+ specialChars);
		}
		return true;
	}

	protected static boolean isValidEmailAddress(String email) throws AddressException {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			return true;
	}

	protected static boolean doesPasswordMeetsStandard(final String pwd) {

		List<Rule> rules = new ArrayList<>();
		// Rule 1: Password length should be in between
		// 8 and 16 characters
		rules.add(new LengthRule(8, 16));
		// Rule 2: No whitespace allowed
		rules.add(new WhitespaceRule());
		// Rule 3.a: At least one Upper-case character
		rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
		// Rule 3.b: At least one Lower-case character
		rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
		// Rule 3.c: At least one digit
		rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
		// Rule 3.d: At least one special character
		rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

		PasswordValidator validator = new PasswordValidator(rules);
		PasswordData password = new PasswordData(pwd);
		RuleResult result = validator.validate(password);
		
		if(result.isValid()) {
			return true;
		}
		else {
			throw new BadInputChoiceException("Password didnot meet standard. Please check the password policy !");
		}
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
