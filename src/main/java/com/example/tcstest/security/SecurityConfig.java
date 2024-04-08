package com.example.tcstest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/users", "/users/**", "/invoices", "/invoices/**").permitAll(); // Allow access to /users and /users/{id} without authentication. "/users/**" means 0 or more path segments after "/users"
            auth.anyRequest().authenticated(); // Require authentication for all other requests
        })
                .csrf(AbstractHttpConfigurer::disable) // POST request won't work unless I put this line
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}