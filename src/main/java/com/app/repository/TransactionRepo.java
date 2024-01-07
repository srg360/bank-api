package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String>{

}
