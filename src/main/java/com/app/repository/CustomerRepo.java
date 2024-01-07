package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
	
	Optional<List<Customer>> findByEmail(String email);
	
	Boolean existsByAccountNumber(String accountNumber);
	
	Customer findByAccountNumber(String accountNumber);

}
