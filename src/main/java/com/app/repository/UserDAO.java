package com.app.repository;

import org.springframework.stereotype.Repository;

import com.app.entity.User;

@Repository
public interface UserDAO {
	
	User findByUsername(String username);
	
}
