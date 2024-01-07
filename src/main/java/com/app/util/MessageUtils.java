package com.app.util;

public interface MessageUtils {
	
	String ACCOUNT_EXISTS_CODE="0001";
	String ACCOUNT_EXISTS_MESSAGE="Customer already has an account.";
	String ACCOUNT_CREATION_SUCCESS_CODE="0002";
	String ACCOUNT_CREATION_SUCCESS_MESSAGE="Account has been successfully created.";
	String ACCOUNT_NOT_EXIST_CODE="0003";
	String ACCOUNT_NOT_EXIST_MESSAGE="Account number does not exist.";
	String ACCOUNT_FOUND_CODE="0004";
	String ACCOUNT_FOUND_SUCCESS_MESSAGE="User Account Found.";
	String ACCOUNT_CREDIT_SUCCESS_CODE="0005";
	String ACCOUNT_CREDIT_SUCCESS_MESSAGE="Amount successfully credited.";
	String ACCOUNT_INSUFFICIENT_BALANCE_CODE="0006";
	String ACCOUNT_INSUFFICIENT_BALANCE_MESSAGE="Balance is insufficient.";
	String ACCOUNT_DEBIT_SUCCESS_CODE="0007";
	String ACCOUNT_DEBIT_SUCCESS_MESSAGE="Amount successfully debited.";
	String ACCOUNT_TARGET_NOT_FOUND_CODE="0008";
	String ACCOUNT_TARGET_NOT_FOUND_MESSAGE="Target account does not exist";
	String ACCOUNT_TRANSFER_SUCCESS_CODE="0009";
	String ACCOUNT_TRANSFER_SUCCESS_MESSAGE="Amount has been successfully transferred";
	String STATEMENT_MAILED_SUCCESS_CODE="0010";
	String STATEMENT_MAILED_SUCCESS_MESSAGE="Bank statement sent to customer's email";
	int DATE_ERROR_CODE=11;
	String DATE_ERROR_MESSAGE="End date should not be prior of start date";
	
}
