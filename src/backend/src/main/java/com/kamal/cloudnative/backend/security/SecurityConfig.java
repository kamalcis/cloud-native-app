package com.kamal.cloudnative.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for dev
                .csrf(csrf -> csrf.disable())
                // Configure access rules
                .authorizeHttpRequests(auth -> auth
                        // Permit Swagger UI and API docs
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html")
                        .permitAll()
                        // Permit all other endpoints (no login prompt)
                        .anyRequest().permitAll());

        // No httpBasic or form login needed in dev
        return http.build();
    }
}
