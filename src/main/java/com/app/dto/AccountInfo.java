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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
	@Schema(name="User Account Name")
	private String accountName;
	@Schema(name="User Account Balance")
	private BigDecimal accountBalance;
	@Schema(name="User Account Number")
	private String accountNumber;

}
