package com.spring.security.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.jwt.config.YmlConfig;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private YmlConfig ymlConfig;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		return new User(ymlConfig.getUsername(), ymlConfig.getPassword(), new ArrayList<>());
	}
}