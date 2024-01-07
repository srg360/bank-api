package com.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
	@Schema(name="Bank Response Code")
	private String responseCode;
	@Schema(name="Bank Response Message")
	private String responseMessage;
	@Schema(name="User Account Summary")
	private AccountInfo acccountInfo;
}
