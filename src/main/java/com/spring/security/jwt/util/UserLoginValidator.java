package com.spring.security.jwt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.springframework.stereotype.Repository;

@Repository
public class UserLoginValidator implements UserLoginValidatable {

	// @Value("${user.login.file}")
	private String propFileLocation = "src/main/resources/UsersLog.properties";
	
	
	static Properties loadPropFile() throws FileNotFoundException, IOException {
		InputStream input = new FileInputStream("src/main/resources/UsersLog.properties");
		Properties prop = new Properties();
		prop.load(input);
		return prop;
	}

	/***
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public void save(String userName, String password) throws IOException {

		try {
			checkUserNameAvailability(userName);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}

		final Properties properties = new Properties();
		try (OutputStream outputStream = new FileOutputStream(propFileLocation, true)) {
			properties.setProperty(userName, password);
			properties.store(outputStream, null);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws IOException
	 */
	public boolean login(final String userName, final String passwordEnteredByUser) throws IOException {

		final Properties properties = getPropertiesFileValues(propFileLocation);

		String passwordRetrivedFromBackend = properties.getProperty(userName);

		if (Optional.ofNullable(passwordRetrivedFromBackend).isPresent() && passwordRetrivedFromBackend.equals(passwordEnteredByUser)) {
			return true;

		} else {
			throw new IllegalArgumentException("UserName doesnot exist in the system. Check your input.");
		}

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
	
	private final void deleteByUserNameAndPassword(final String userName, final String password) throws IllegalArgumentException, IOException {

		File myFile = new File(propFileLocation);
		Properties properties = new Properties();
		properties.load(new FileInputStream(myFile));
		properties.remove(userName, password);
		OutputStream out = new FileOutputStream(myFile);
		properties.store(out, null);
	}

		
	private final String recoverPasswordByUserName(final String userName) throws IllegalArgumentException, IOException {

		String password = null;
		InputStream input = new FileInputStream(propFileLocation);
		Properties prop = new Properties();
		prop.load(input);

		for (Object val : prop.keySet()) {
			if (((String) val).equalsIgnoreCase(userName)) {
				password = prop.getProperty(userName);
			}
		}
		if (Objects.isNull(password)) {
			throw new IllegalArgumentException("User name: " + userName + " doesnot exist. Please check the input.");
		}
		else {
			return password;	
		}

	}

	private final void checkUserNameAvailability(String userName) throws IllegalArgumentException, IOException {

		Properties prop = loadPropFile();
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
