package com.spring.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.pojo.AuthenticationResponse;
import com.spring.security.jwt.service.MyUserDetailsService;
import com.spring.security.jwt.util.JwtUtil;

/**
 * 
 * @author SauravSaurock Date: April, 2021
 *
 */
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@RequestMapping(value = "/v1/token", method = RequestMethod.GET)
	public ResponseEntity<?> createAuthenticationToken( @RequestParam("userName") final String userName, 
														@RequestParam("password") final String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password", e);
		} catch (Exception ex) {
			throw ex;
		}

	}

}
