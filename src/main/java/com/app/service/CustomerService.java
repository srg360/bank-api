package com.app.service;


import com.app.dto.BankResponse;
import com.app.dto.CreditDebitRequest;
import com.app.dto.CustomerRequest;
import com.app.dto.EnquiryRequest;
import com.app.dto.TransferRequest;

public interface CustomerService {

	BankResponse createAccount(CustomerRequest customerRequest);
	
	BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	
	String nameEnquiry(EnquiryRequest enquiryRequest);
	
	BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
	
	BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
	
	BankResponse transfer(TransferRequest transferRequest);
	
}
