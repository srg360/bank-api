package com.app.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Transaction;
import com.app.service.BankStatement;
import com.app.util.AppUtils;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bankstatement")
@AllArgsConstructor
@Tag(name="Customer Transaction Tracking API")
public class TransactionController {
	
	@Autowired
	private BankStatement bankStatement;
	
	@Operation(summary = "Account Creation",description = "Generating PDF of customer's bank statement and sending to customer's email")
	@ApiResponse(responseCode = "200",description = "Http status: OK")
	@GetMapping
	public ResponseEntity<List<Transaction>> getBankStatement(@RequestParam String accountNumber,@RequestParam String startDt,@RequestParam String endDt)
			throws FileNotFoundException, DocumentException {
		AppUtils.validateDates(startDt, endDt);
		return new ResponseEntity<>(bankStatement.generateStatement(accountNumber, startDt, endDt),HttpStatus.OK);
	}

}
