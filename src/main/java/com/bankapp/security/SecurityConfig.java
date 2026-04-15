package com.bankapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())

	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll()
	            .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
	            .requestMatchers("/api/account/**").hasAnyAuthority("USER", "ADMIN") // better naming
	            .anyRequest().authenticated()
	        )

	        // 🔥 VERY IMPORTANT (JWT = stateless)
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )

	        // 🔥 Add JWT filter
	        .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	
    }
}