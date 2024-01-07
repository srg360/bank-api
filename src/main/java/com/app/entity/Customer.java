package com.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="CUSTOMER_TX")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String accountNumber;
	private BigDecimal accountBalance;
	private String address;
	private String phoneNumber;
	private String alternativePhoneNumber;
	@CreationTimestamp
	private LocalDateTime createdDt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	private String status;
	
}
