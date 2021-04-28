package com.spring.security.jwt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class CryptPassword{

	final static String salt = KeyGenerators.string().generateKey();

	public final static String getEncodedPassword(final String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	public final static boolean doesStoredPasswordMatchWithUserPassword(String rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);

	}

	public final static String encodePassword(final String password) {

		final TextEncryptor encryptor = Encryptors.text(password, salt);
		return encryptor.encrypt(password);

	}

	public final static String decodePassword(final String password) {

		TextEncryptor decryptor = Encryptors.text(password, salt);
		return decryptor.decrypt(password);
	}
	
}
