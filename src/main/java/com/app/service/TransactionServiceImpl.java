package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.TransactionDTO;
import com.app.entity.Transaction;
import com.app.repository.TransactionRepo;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Override
	public void saveTransaction(TransactionDTO transactionDTO) {
		Transaction transaction=Transaction.builder()
				.accountNumber(transactionDTO.getAccountNumber())
				.amount(transactionDTO.getAmount())
				.balanceAmount(transactionDTO.getBalanceAmount())
				.status(transactionDTO.getStatus())
				.transactionType(transactionDTO.getTransactionType()).build();
		transactionRepo.save(transaction);
	}
	
	

}
