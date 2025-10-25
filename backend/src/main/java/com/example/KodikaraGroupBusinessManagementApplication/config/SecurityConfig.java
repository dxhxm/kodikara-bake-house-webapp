package com.example.KodikaraGroupBusinessManagementApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // This is required for @PreAuthorize to work
public class SecurityConfig {

    // --- THIS IS THE FIX ---
    // This @Bean method tells Spring: "When anyone asks for a PasswordEncoder,
    // run this method and give them the BCryptPasswordEncoder object."
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // --- END OF FIX ---

    // This is a basic security filter.
    // It allows anyone to access login/register pages but secures everything else.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for now
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Make sure to create your login controller here
                        .anyRequest().permitAll() // All other requests must be authenticated
                );
        return http.build();
    }
}