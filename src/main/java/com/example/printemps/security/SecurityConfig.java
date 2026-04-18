package com.example.printemps.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/catalog/**").hasAnyRole("READER", "LIBRARIAN", "ADMIN")
                        .requestMatchers("/api/holds/**").hasAnyRole("READER", "LIBRARIAN", "ADMIN")
                        .requestMatchers("/api/loans/**").hasAnyRole("READER", "LIBRARIAN", "ADMIN")
                        .requestMatchers("/api/users/policies/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("READER", "LIBRARIAN", "ADMIN")
                        .requestMatchers("/api/penalties/**").hasAnyRole("LIBRARIAN", "ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            objectMapper.writeValue(response.getOutputStream(), Map.of(
                                    "timestamp", Instant.now().toString(),
                                    "status", 401,
                                    "error", "Unauthorized",
                                    "message", "Authentication is required to access this resource",
                                    "path", request.getRequestURI()
                            ));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            objectMapper.writeValue(response.getOutputStream(), Map.of(
                                    "timestamp", Instant.now().toString(),
                                    "status", 403,
                                    "error", "Access Denied",
                                    "message", "You do not have permission to access this resource",
                                    "path", request.getRequestURI()
                            ));
                        })
                )
                .build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails reader1 = User.withUsername("reader1")
                .password("{noop}password")
                .roles("READER")
                .build();

        UserDetails reader2 = User.withUsername("reader2")
                .password("{noop}password")
                .roles("READER")
                .build();

        UserDetails reader3 = User.withUsername("reader3")
                .password("{noop}password")
                .roles("READER")
                .build();

        UserDetails librarian1 = User.withUsername("librarian1")
                .password("{noop}password")
                .roles("LIBRARIAN")
                .build();

        UserDetails admin1 = User.withUsername("admin1")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(reader1, reader2, reader3, librarian1, admin1);
    }
}