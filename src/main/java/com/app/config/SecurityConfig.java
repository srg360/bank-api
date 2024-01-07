package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.app.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	/*
	 * @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		/* authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); */
		return authenticationProvider;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
		 
		httpSecurity.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST,"/api/customer").hasRole("BANKER")
										   .requestMatchers(HttpMethod.GET,"/api/customer/**").hasRole("BANKER")
										   .requestMatchers(HttpMethod.GET,"/bankstatement").hasRole("BANKER")
										   .requestMatchers(HttpMethod.POST, "/api/customer/**").hasRole("BANKER"));
				
		httpSecurity.httpBasic(Customizer.withDefaults());
		
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 
		 return httpSecurity.build();
	}

}
