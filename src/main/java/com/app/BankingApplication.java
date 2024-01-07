package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@EnableAsync
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SRG Bank App",description = "Backend REST APIs for core banking operations",version = "v1.0.0"
						,contact = @Contact(name="Shrihari Gosavi",email = "shrihari.gosavi2@gmail.com")))
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

}
