package com.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.AccountInfo;
import com.app.dto.BankResponse;
import com.app.dto.CreditDebitRequest;
import com.app.dto.CustomerRequest;
import com.app.dto.EmailDetails;
import com.app.dto.EnquiryRequest;
import com.app.dto.TransactionDTO;
import com.app.dto.TransferRequest;
import com.app.entity.Customer;
import com.app.repository.CustomerRepo;
import com.app.util.AccountUtils;
import com.app.util.MessageUtils;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TransactionService transactionService;

	@Override
	public BankResponse createAccount(CustomerRequest customerRequest) {
		
		Optional<List<Customer>> userList = customerRepo.findByEmail(customerRequest.getEmail());
		Boolean isUserAvailable=userList.get().size() > 0;
		
		if(isUserAvailable) {
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_EXISTS_CODE)
					.responseMessage(MessageUtils.ACCOUNT_EXISTS_MESSAGE)
					.acccountInfo(null)
					.build();
		}
		Customer newCustomer=Customer.builder()
				.firstName(customerRequest.getFirstName())
				.lastName(customerRequest.getLastName())
				.gender(customerRequest.getGender())
				.address(customerRequest.getAddress())
				.email(customerRequest.getEmail())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.phoneNumber(customerRequest.getPhoneNumber())
				.alternativePhoneNumber(customerRequest.getAlternativePhoneNumber())
				.status("ACTIVE")
				.build();
				
		Customer savedUser=customerRepo.save(newCustomer);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.receipient(savedUser.getEmail())
				.messageBody("Congratulations, your account has been created successfully created.\nYour Account Details:\n"
						+ "Account Name: "+savedUser.getFirstName()+" "+savedUser.getLastName()+"\n"
						+ "Account Number: "+savedUser.getAccountNumber())
				.subject("ACCOUNT CREATION").build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder()
				.responseCode(MessageUtils.ACCOUNT_CREATION_SUCCESS_CODE)
				.responseMessage(MessageUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
				.acccountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
						.accountNumber(savedUser.getAccountNumber()).build())
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
		
		Boolean isAccountExistBoolean=customerRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if(!isAccountExistBoolean) {
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(MessageUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.acccountInfo(null)
					.build();
		}
		Customer foundUser=customerRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return BankResponse.builder()
				.responseCode(MessageUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(MessageUtils.ACCOUNT_FOUND_SUCCESS_MESSAGE)
				.acccountInfo(AccountInfo.builder()
						.accountBalance(foundUser.getAccountBalance())
						.accountNumber(foundUser.getAccountNumber())
						.accountName(foundUser.getFirstName()+" "+foundUser.getLastName()).build())
				.build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest enquiryRequest) {
		Boolean isAccountExistBoolean=customerRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if(!isAccountExistBoolean) {
			return MessageUtils.ACCOUNT_NOT_EXIST_MESSAGE;
		}
		Customer foundUser=customerRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return foundUser.getFirstName()+" "+foundUser.getLastName();
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
		
		Boolean isAccountExistBoolean=customerRepo.existsByAccountNumber(creditDebitRequest.getAccountNumber());
		if(!isAccountExistBoolean) {
			
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(MessageUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.acccountInfo(null)
					.build();
		}
			
		
		Customer userToCredit=customerRepo.findByAccountNumber(creditDebitRequest.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
		
		Customer updatedUser=customerRepo.save(userToCredit);
		
		TransactionDTO transactionDTO=TransactionDTO.builder()
				.accountNumber(updatedUser.getAccountNumber())
				.amount(creditDebitRequest.getAmount())
				.transactionType("CREDIT").status("SUCCESS")
				.balanceAmount(updatedUser.getAccountBalance()).build();
				
		transactionService.saveTransaction(transactionDTO);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.receipient(updatedUser.getEmail())
				.messageBody("Rs. "+creditDebitRequest.getAmount()+"/- credited to your account.\n"
						+ "Your current balance is Rs. "+updatedUser.getAccountBalance()+"/-")
				.subject("AMOUNT CREDITED").build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder().responseCode(MessageUtils.ACCOUNT_CREDIT_SUCCESS_CODE)
				.responseMessage(MessageUtils.ACCOUNT_CREDIT_SUCCESS_MESSAGE)
				.acccountInfo(AccountInfo.builder()
						.accountNumber(updatedUser.getAccountNumber())
						.accountBalance(updatedUser.getAccountBalance())
						.accountName(updatedUser.getFirstName()+" "+updatedUser.getLastName())
						.build())
				. build();
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
		
		Boolean isAccountExistBoolean=customerRepo.existsByAccountNumber(creditDebitRequest.getAccountNumber());
		if(!isAccountExistBoolean)
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(MessageUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.acccountInfo(null)
					.build();
		
		Customer userToDebit=customerRepo.findByAccountNumber(creditDebitRequest.getAccountNumber());
		Boolean isBalanceSufficient=userToDebit.getAccountBalance().compareTo(creditDebitRequest.getAmount())>=0;
		if(!isBalanceSufficient)
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_INSUFFICIENT_BALANCE_CODE)
					.responseMessage(MessageUtils.ACCOUNT_INSUFFICIENT_BALANCE_MESSAGE)
					.acccountInfo(AccountInfo.builder()
							.accountNumber(userToDebit.getAccountNumber())
							.accountBalance(userToDebit.getAccountBalance())
							.accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName())
							.build())
					.build();
		
		userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
		
		Customer updatedUser=customerRepo.save(userToDebit);
		
		TransactionDTO transactionDTO=TransactionDTO.builder()
				.accountNumber(updatedUser.getAccountNumber())
				.amount(creditDebitRequest.getAmount())
				.transactionType("DEBIT").status("SUCCESS")
				.balanceAmount(updatedUser.getAccountBalance()).build();
				
		transactionService.saveTransaction(transactionDTO);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.receipient(updatedUser.getEmail())
				.messageBody("Rs."+creditDebitRequest.getAmount()+"/- debited from your account.\n"
						+ "Your current balance is Rs."+updatedUser.getAccountBalance()+"/-")
				.subject("AMOUNT DEBITED").build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder()
				.responseCode(MessageUtils.ACCOUNT_DEBIT_SUCCESS_CODE)
				.responseMessage(MessageUtils.ACCOUNT_DEBIT_SUCCESS_MESSAGE)
				.acccountInfo(AccountInfo.builder()
						.accountNumber(updatedUser.getAccountNumber())
						.accountBalance(updatedUser.getAccountBalance())
						.accountName(updatedUser.getFirstName()+" "+updatedUser.getLastName())
						.build())
				.build();
	}

	@Override
	public BankResponse transfer(TransferRequest transferRequest) {
		
		Customer userToDebit=customerRepo.findByAccountNumber(transferRequest.getSourceAccountNumber());
		Boolean isBalanceSufficient=userToDebit.getAccountBalance().compareTo(transferRequest.getAmount())>=0;
		if(!isBalanceSufficient)
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_INSUFFICIENT_BALANCE_CODE)
					.responseMessage(MessageUtils.ACCOUNT_INSUFFICIENT_BALANCE_MESSAGE)
					.acccountInfo(AccountInfo.builder()
							.accountNumber(userToDebit.getAccountNumber())
							.accountBalance(userToDebit.getAccountBalance())
							.accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName())
							.build())
					.build();
		
		Boolean isAccountExistBoolean=customerRepo.existsByAccountNumber(transferRequest.getTargetAccountNumber());
		if(!isAccountExistBoolean)
			return BankResponse.builder()
					.responseCode(MessageUtils.ACCOUNT_TARGET_NOT_FOUND_CODE)
					.responseMessage(MessageUtils.ACCOUNT_TARGET_NOT_FOUND_MESSAGE)
					.acccountInfo(null)
					.build();
		
		userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(transferRequest.getAmount()));
		
		Customer debitedUser=customerRepo.save(userToDebit);
		
		Customer userToCredit=customerRepo.findByAccountNumber(transferRequest.getTargetAccountNumber());
		
		
		
		TransactionDTO transactionDTO=TransactionDTO.builder()
				.accountNumber(userToCredit.getAccountNumber())
				.amount(transferRequest.getAmount())
				.transactionType("CREDIT").status("SUCCESS")
				.balanceAmount(userToCredit.getAccountBalance()).build();
		
		transactionService.saveTransaction(transactionDTO);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.receipient(debitedUser.getEmail())
				.messageBody("Rs."+transferRequest.getAmount()+"/- has been transfered from your account to "+userToCredit.getFirstName()+" "+userToCredit.getLastName()+".\n"
						+ "Your current balance is Rs."+debitedUser.getAccountBalance()+"/-")
				.subject("AMOUNT DEBITED").build();
		
		emailService.sendEmailAlert(emailDetails);
		
		
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(transferRequest.getAmount()));
		
		Customer creditedUser=customerRepo.save(userToCredit);
		
		emailDetails=EmailDetails.builder()
				.receipient(creditedUser.getEmail())
				.messageBody("Rs. "+transferRequest.getAmount()+"/- received from"+creditedUser.getFirstName()+" "+creditedUser.getLastName()+" to your account.\n"
						+ "Your current balance is Rs. "+creditedUser.getAccountBalance()+"/-")
				.subject("AMOUNT CREDITED").build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder()
				.responseCode(MessageUtils.ACCOUNT_TRANSFER_SUCCESS_CODE)
				.responseMessage(MessageUtils.ACCOUNT_TRANSFER_SUCCESS_MESSAGE)
				.acccountInfo(null).build();
	}

}
