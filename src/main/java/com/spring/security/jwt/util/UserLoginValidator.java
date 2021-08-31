package com.spring.security.jwt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.spring.security.jwt.config.YmlConfig;

@Repository
public class UserLoginValidator implements UserLoginValidatable {

	 @Autowired
	 private YmlConfig  YmlConfig;
	 
	/***
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public void save(String userName, String password) throws Exception {

		saveUserData(userName, password);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	public boolean login(final String userName, final String passwordEnteredByUser) throws Exception {

		return loginUser(userName, passwordEnteredByUser);

	}

	/**
	 * 
	 * @param username
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public String recover(final String username) throws IllegalArgumentException, IOException {

		return recoverPasswordByUserName(username);
	}

	/**
	 * @param username
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void delete(final String username, final String password) throws IllegalArgumentException, IOException {

		deleteByUserNameAndPassword(username, password);
	}
	
	
	private void saveUserData(String userName, String password) throws IOException, Exception {
		try {
			checkUserNameAvailability(userName);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}

		final Properties properties = new Properties();
		try (OutputStream outputStream = new FileOutputStream(YmlConfig.getPropFilePath(), true)) {
			
			BCryptPasswordEncoder obj = new BCryptPasswordEncoder();
			properties.setProperty(userName, obj.encode(password));
			properties.store(outputStream, null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private boolean loginUser(final String userName, final String rawPwd) throws Exception {

		final Properties properties = getPropertiesFileValues(YmlConfig.getPropFilePath());

		final String encodedPwd = properties.getProperty(userName);

		Assert.isTrue(StringUtils.hasText(encodedPwd), "Username not found");

		final BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

		final boolean doesMatches = cryptPasswordEncoder.matches(rawPwd, encodedPwd);

		Assert.isTrue(doesMatches, "Password is incorrect. try again !");

		return doesMatches;
	}
	
	
	
	private final String recoverPasswordByUserName(final String userName) throws IllegalArgumentException, IOException {

		String password = null;
		InputStream input = new FileInputStream(YmlConfig.getPropFilePath());
		Properties prop = new Properties();
		prop.load(input);

		for (Object val : prop.keySet()) {
			if (((String) val).equalsIgnoreCase(userName)) {
				password =prop.getProperty(userName);
			}
		}
		if (Objects.isNull(password)) {
			throw new IllegalArgumentException("User name: " + userName + " doesnot exist. Please check the input.");
		}
		else {
			return password;	
		}

	}
	
	private final void deleteByUserNameAndPassword(final String userName, final String password) throws IllegalArgumentException, IOException {

		File myFile = new File(YmlConfig.getPropFilePath());
		Properties properties = new Properties();
		properties.load(new FileInputStream(myFile));
		properties.remove(userName, password);
		OutputStream out = new FileOutputStream(myFile);
		properties.store(out, null);
	}


	private final void checkUserNameAvailability(String userName) throws IllegalArgumentException, IOException {
			
		InputStream input = new FileInputStream(YmlConfig.getPropFilePath());
		Properties prop = new Properties();
		prop.load(input);
		prop.keySet().forEach(x -> doesUserNameExist(x, userName));

	}

	private void doesUserNameExist(Object x, String userName) {

		if (((String) x).equalsIgnoreCase(userName)) {
			throw new IllegalArgumentException(userName + " already exist. Sorry Choose another username !");
		}
	}

	/**
	 * 
	 * @param propFileLocation
	 * @return
	 * @throws IOException
	 */
	private final Properties getPropertiesFileValues(final String propFileLocation) throws IOException {

		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(propFileLocation);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}
	

}
