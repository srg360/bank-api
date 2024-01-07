package com.app.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
	
	@Schema(name="Source Account Number")
	private String sourceAccountNumber;
	@Schema(name="Target Account Number")
	private String targetAccountNumber;
	@Schema(name="Amount to be send")
	private BigDecimal amount;
	
	

}
