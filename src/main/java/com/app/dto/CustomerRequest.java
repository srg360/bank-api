package com.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequest {
	@Schema(name="Customer First Name")
	private String firstName;
	@Schema(name="Customer Last Name")
	private String lastName;
	@Schema(name="Customer Gender")
	private String gender;
	@Schema(name="Customer Email")
	private String email;
	@Schema(name="Customer Address")
	private String address;
	@Schema(name="Customer Phone Number")
	private String phoneNumber;
	@Schema(name="Customer Alternative Phone Number")
	private String alternativePhoneNumber;
	@Schema(name="User account password")
	private String password;
}
