package com.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User findByUsername(String username) {
		
		TypedQuery<User> query=entityManager.createQuery("from User where username=:uname",User.class).setParameter("uname",username);
		User user=null;
		try {
			user=query.getSingleResult();
		} catch (Exception e) {
			user=null;
		}
		
		return user;
	}

}
