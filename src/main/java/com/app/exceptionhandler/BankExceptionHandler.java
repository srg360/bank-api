package com.app.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.dto.ErrorResponse;
import com.app.exception.InvalidDateException;
import com.app.util.MessageUtils;

@RestControllerAdvice
public class BankExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException exception){
		Map<String, String> resp=new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidDateException exception){
		ErrorResponse errorResponse=ErrorResponse.builder().errorCode(MessageUtils.DATE_ERROR_CODE).errorMessage(exception.getMessage())
				.build();
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
//	@ExceptionHandler(value = Exception.class)
//	public ResponseEntity<ErrorResponse> handleException(Exception exception){
//		ErrorResponse errorResponse=ErrorResponse.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).errorMessage(exception.getMessage())
//				.build();
//		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
