package com.app.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDebitRequest {
	
	@Schema(name="User Account Number")
	private String accountNumber;
	@Schema(name="Amount to be credited/debited")
	private BigDecimal amount;
}
