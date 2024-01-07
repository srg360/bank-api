package com.app.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.dto.ErrorResponse;
import com.app.exception.InvalidDateException;
import com.app.util.MessageUtils;

@ControllerAdvice
public class BankExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(InvalidDateException exception){
		ErrorResponse errorResponse=ErrorResponse.builder().errorCode(MessageUtils.DATE_ERROR_CODE).errorMessage(exception.getMessage())
				.build();
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exception){
		ErrorResponse errorResponse=ErrorResponse.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).errorMessage(exception.getMessage())
				.build();
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
