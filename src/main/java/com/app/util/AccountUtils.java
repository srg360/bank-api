package com.app.util;

import java.time.Year;

public class AccountUtils {
	
	public static String generateAccountNumber() {
		Year currentYear=Year.now();
		int min=100000;
		int max=999999;
		
		//generate random number between min and max
		int randomNumber=(int) Math.floor(Math.random()*(max-min+1)+min);
		//convert current year and randomNumber to strings, then concatenate
		String year=String.valueOf(currentYear);
		String randomNo=String.valueOf(randomNumber);
		StringBuilder accountNumber=new StringBuilder();
		
		return accountNumber.append(year).append(randomNo).toString();
	}
}
