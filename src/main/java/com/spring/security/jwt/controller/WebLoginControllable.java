package com.spring.security.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.spring.security.jwt.pojo.DefaultRequest;

public interface WebLoginControllable {

	/**
	 * takes user request to create account. 
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ResponseEntity<?> signUpUser( @RequestBody final DefaultRequest defaultRequestForm, 
								  @RequestParam("excludePwdPolicy") boolean  excludePwdPolicy,
								  final WebRequest request ) throws Exception;
	
	/**
	 * takes user request to login into account using existing user account.
	 * @param defaultRequestForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ResponseEntity<?> loginUser( @RequestBody final DefaultRequest defaultRequestForm, 
											  final WebRequest request) throws Exception;
	
	
	/**
	 * takes user request to recover password via UserName.
	 * @param userName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ResponseEntity<?> recoverUser( @RequestParam("userName") final String userName, final WebRequest request) throws Exception;
	
	
	/**
	 * takes user request to delete user account.
	 * @param defaultRequestForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ResponseEntity<?> deleteUser( @RequestBody final DefaultRequest defaultRequestForm, 
											   final WebRequest request) throws Exception;
	
	
}
