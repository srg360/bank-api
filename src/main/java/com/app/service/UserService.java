package com.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.entity.User;

public interface UserService extends UserDetailsService {
	
	User findUserByUsername(String username);

}
