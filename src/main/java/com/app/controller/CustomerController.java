package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BankResponse;
import com.app.dto.CreditDebitRequest;
import com.app.dto.CustomerRequest;
import com.app.dto.EnquiryRequest;
import com.app.dto.TransferRequest;
import com.app.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Account Management APIs")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Operation(summary = "Account Creation",description = "Creating a new customer account and assigning new 10 digits account number")
	@ApiResponse(responseCode = "201",description = "Http status: CREATED")
	@PostMapping
	protected ResponseEntity<BankResponse> createAccount(@Valid @RequestBody CustomerRequest customerRequest) {
		return new ResponseEntity<>(customerService.createAccount(customerRequest),HttpStatus.CREATED);
	}
	
	@Operation(summary = "Account Information",description = "Given an account number, fetching customer's account information")
	@ApiResponse(responseCode = "200",description = "Http status: OK")
	@GetMapping("/info")
	protected ResponseEntity<BankResponse> accountEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
		return new ResponseEntity<>(customerService.balanceEnquiry(enquiryRequest),HttpStatus.OK);
	}
	
	@Operation(summary = "Account credit",description = "Crediting amount to the customer's account against account number sending notification "
			+ "to the account holder through email")
	@ApiResponse(responseCode = "200",description = "Http status: OK")
	@PostMapping("/credit")
	protected ResponseEntity<BankResponse> creditAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
		return new ResponseEntity<>(customerService.creditAccount(creditDebitRequest),HttpStatus.OK);
	}
	
	@Operation(summary = "Account debit",description = "Debiting money from the customer's account against account number and "
			+ "sending notification to account holder through email")
	@ApiResponse(responseCode = "200",description = "Http status: OK")
	@PostMapping("/debit")
	protected ResponseEntity<BankResponse> debitAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
		return new ResponseEntity<>(customerService.debitAccount(creditDebitRequest),HttpStatus.OK);
	}
	
	@Operation(summary = "Amount Transfer",description = "Transfering amount from one to another account through account number "
			+ "and sending notification to source and target account holder by email")
	@ApiResponse(responseCode = "200",description = "Http status: OK")
	@PostMapping("/transfer")
	protected ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest transferRequest) {
		return new ResponseEntity<>(customerService.transfer(transferRequest),HttpStatus.OK);
	}

}
