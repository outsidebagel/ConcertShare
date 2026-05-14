package com.nick.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth -> auth
							.requestMatchers("/").permitAll()
							.requestMatchers("/register").permitAll()
							.requestMatchers("/process_register").permitAll()
							.requestMatchers("/login").permitAll()

	.anyRequest().authenticated()
	)
	.formLogin(form -> form
					.loginPage("/login")
	.defaultSuccessUrl("/lollapolloza.html")
	.permitAll()
	)
	.logout(logout -> logout
	.logoutSuccessUrl("/")
	);
	return http.build();
	}
	
	
	
}
