package com.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.app.exception.InvalidDateException;

public class AppUtils {
	
	public static void validateDates(String startDate,String endDate)throws InvalidDateException {
		
		LocalDate stDt=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		LocalDate edDt=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
		
		if(edDt.isBefore(stDt))
			throw new InvalidDateException(MessageUtils.DATE_ERROR_MESSAGE);
		
	}

}
