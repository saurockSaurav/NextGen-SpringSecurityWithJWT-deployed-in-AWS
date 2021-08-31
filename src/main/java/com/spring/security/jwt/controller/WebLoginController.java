package com.spring.security.jwt.controller;

import java.text.MessageFormat;
import java.util.Date;

import javax.mail.internet.AddressException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.spring.security.jwt.exception.CustomizedExceptionHandler;
import com.spring.security.jwt.pojo.DefaultReponse;
import com.spring.security.jwt.pojo.DefaultRequest;
import com.spring.security.jwt.pojo.PasswordRecoverResponse;
import com.spring.security.jwt.util.UserLoginValidator;
import com.spring.security.password.policy.BadInputChoiceException;


/**
 * 
 * @author SauravSaurock
 * Date: April, 2021
 *
 */
@RestController
@RequestMapping("v1/nextcitizen")
public class WebLoginController extends WebLoginControllerAssert implements WebLoginControllable {
	
	private static final Logger logger = LogManager.getLogger(WebLoginController.class);

	@Autowired
	private UserLoginValidator userLoginValidator;

	@Autowired
	private CustomizedExceptionHandler customizedExceptionHandler;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PutMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> signUpUser (@RequestBody final DefaultRequest defaultRequestForm, 
										 @RequestParam(value = "excludePwdPolicy", required = false ) boolean  excludePwdPolicy,
										 final WebRequest request ) throws Exception {
		
		try {
			
			Assert.notNull(defaultRequestForm, "Customer request cannot be null");
			Assert.isTrue(StringUtils.hasText(defaultRequestForm.getUserName()), "UserName cannot be null or Empty");
			Assert.isTrue(StringUtils.hasText(defaultRequestForm.getPassword()), "Password cannot be null or Empty");
			
			super.validateUnameAndPwd(defaultRequestForm.getUserName(), defaultRequestForm.getPassword(), excludePwdPolicy);
			
			logger.info(MessageFormat.format("SignUp process started for:{0}", defaultRequestForm.getUserName()));
			
			userLoginValidator.save(defaultRequestForm.getUserName(), defaultRequestForm.getPassword());
			logger.info(MessageFormat.format("SignUp process completed for:{0}", defaultRequestForm.getUserName()));
			
			return new ResponseEntity<DefaultReponse>(new DefaultReponse(new Date(), "User Account created successfully", HttpStatus.CREATED.getReasonPhrase()), HttpStatus.CREATED);

		} catch (IllegalArgumentException | AddressException| BadInputChoiceException e) {
			logger.error(MessageFormat.format("Error occured during SignUp process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e));
			return customizedExceptionHandler.handleBadRequestException(e, request);
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error occured during SignUp process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e ));
			return customizedExceptionHandler.handleInternalServerException(e, request);
		}
	}

	/***
	 * 
	 * 
	 * @param loginForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> loginUser(@RequestBody final DefaultRequest defaultRequestForm, final WebRequest request) throws Exception {
		
		DefaultReponse loginReponse = null;

		try {
			super.validateUnameAndPwd(defaultRequestForm.getUserName(), defaultRequestForm.getPassword());
			logger.info(MessageFormat.format("Login process started for:{0}", defaultRequestForm.getUserName()));
			if (userLoginValidator.login(defaultRequestForm.getUserName(), defaultRequestForm.getPassword())) {
				loginReponse = new DefaultReponse(new Date(), "Welcome back user : " + defaultRequestForm.getUserName(), HttpStatus.ACCEPTED.getReasonPhrase());
			}
			logger.info(MessageFormat.format("Login process completed for:{0}", defaultRequestForm.getUserName()));
		} catch (IllegalArgumentException e) {
			logger.error(MessageFormat.format("Error occured during Login process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e.getMessage()));
			return customizedExceptionHandler.handleBadRequestException(e, request);
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error occured during Login process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e));
			return customizedExceptionHandler.handleInternalServerException(e, request);
		}
		return new ResponseEntity<DefaultReponse>(loginReponse, HttpStatus.CREATED);
	}

	/***
	 * 
	 * 
	 * @param loginForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = "/recover", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> recoverUser(@RequestParam("userName") final String userName, final WebRequest request) throws Exception {
		
		PasswordRecoverResponse passwordRecoverResponse = null;
		
		try {
			super.validateUnameAndPwd(userName, null, false);
			passwordRecoverResponse = new PasswordRecoverResponse(new Date(), userLoginValidator.recover(userName));
			logger.info(MessageFormat.format("Recover  process completed for:{0}", userName));
		} catch (IllegalArgumentException e) {
			logger.error(MessageFormat.format("Error occured during Recover process for user:{0} - cause- {1}", userName, e.getMessage()));
			return customizedExceptionHandler.handleBadRequestException(e, request);
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error occured during Recover process for user:{0} - cause- {1}", userName, e.getMessage()));
			return customizedExceptionHandler.handleInternalServerException(e, request);
		}
		return new ResponseEntity<PasswordRecoverResponse>(passwordRecoverResponse, HttpStatus.ACCEPTED);
	}
	
	/***
	 * 
	 * 
	 * @param loginForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> deleteUser( @RequestBody final DefaultRequest defaultRequestForm, final WebRequest request) throws Exception {
		
		DefaultReponse deleteRespose = null;
		
		try {
			super.validateUnameAndPwd(defaultRequestForm.getUserName(), defaultRequestForm.getPassword(), true);
			
			logger.info(MessageFormat.format("Delete  process started for:{0}", defaultRequestForm.getUserName()));
			
			if (userLoginValidator.login(defaultRequestForm.getUserName(), defaultRequestForm.getPassword())) {
				
				userLoginValidator.delete(defaultRequestForm.getUserName(), defaultRequestForm.getPassword());
				
				deleteRespose = new DefaultReponse(new Date(), "User Deleted: " + defaultRequestForm.getUserName(), HttpStatus.ACCEPTED.getReasonPhrase());
			}
			
			logger.info(MessageFormat.format("Delete  process completed for:{0}", defaultRequestForm.getUserName()));
		} catch (IllegalArgumentException e) {
			logger.error(MessageFormat.format("Error occured during Recover process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e.getMessage()));
			return customizedExceptionHandler.handleBadRequestException(e, request);
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error occured during Recover process for user:{0} - cause- {1}", defaultRequestForm.getUserName(), e.getMessage()));
			return customizedExceptionHandler.handleInternalServerException(e, request);
		}
		return new ResponseEntity<DefaultReponse>(deleteRespose, HttpStatus.ACCEPTED);
	}

}
