package com.app.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
	
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private BigDecimal balanceAmount;
	private String status;
	
}
