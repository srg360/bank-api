package com.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequest {
	
	@NotEmpty(message = "First name cannot be empty")
	@Schema(name="Customer First Name")
	private String firstName;
	
	@NotEmpty(message = "Last name cannot be empty")
	@Schema(name="Customer Last Name")
	private String lastName;
	
	@Pattern(regexp = "[MF]{1}",message = "Gender cannot be other than Male or Female")
	@Schema(name="Customer Gender")
	private String gender;
	
	@Email(message = "Email is invalid")
	@Schema(name="Customer Email")
	private String email;
	
	@NotEmpty(message = "Address cannot be empty")
	@Schema(name="Customer Address")
	private String address;
	
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Primary contact number is invalid")
	@Schema(name="Customer Phone Number")
	private String phoneNumber;
	
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Alternative contact number is invalid")
	@Schema(name="Customer Alternative Phone Number")
	private String alternativePhoneNumber;
	
}
