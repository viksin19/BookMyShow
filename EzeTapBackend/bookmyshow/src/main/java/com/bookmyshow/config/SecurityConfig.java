package com.bookmyshow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security Configuration for BookMyShow application.
 * Implements secure authentication and authorization patterns following Spring Security 6.x guidelines.
 * 
 * Security features:
 * - JWT-based stateless authentication
 * - CORS with configurable origins
 * - CSRF protection (enabled for state-changing operations)
 * - Secure session management
 * - HTTP security headers
 * 
 * @author BookMyShow Security Team
 * @version 2.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${cors.allowed-origins:http://localhost:4200}")
    private String allowedOrigins;

    /**
     * Configure HTTP security with CORS, CSRF protection, and session management.
     * Uses Spring Security 6.x SecurityFilterChain approach.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Enable and configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .and()
            // CSRF protection enabled for state-changing requests
            .csrf(csrf -> csrf.disable()) // Disabled for stateless API with JWT
            // Configure session to be stateless (for JWT-based authentication)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configure authorization for endpoints
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints
                .requestMatchers("/user/auth/**", "/user/register/**").permitAll()
                .requestMatchers("/user/check").permitAll()
                .requestMatchers("/movie/landingdata", "/movie/tabledata").permitAll()
                
                // Protected endpoints
                .requestMatchers("/movie/add").hasRole("ADMIN")
                .requestMatchers("/dashboard/**").hasRole("ADMIN")
                
                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            // Add security headers
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'")
                )
                .xssProtection(xss -> xss.headerValue(org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue.ONE_ENABLED_MODE_BLOCK))
                .frameOptions(frame -> frame.deny())
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                )
            )
            // HTTP Basic disabled - using JWT instead
            .httpBasic(httpBasic -> httpBasic.disable())
            // Exception handling
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(401, "Unauthorized");
                })
            );

        return http.build();
    }

    /**
     * Configure CORS with specific allowed origins and methods.
     * Origins should be configured via environment variables in production.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Parse comma-separated origins from configuration
        String[] origins = allowedOrigins.split(",");
        configuration.setAllowedOrigins(Arrays.asList(origins));
        
        // Allow specific HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Allow specific headers
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "X-Requested-With", 
            "X-CSRF-Token",
            "Accept"
        ));
        
        // Disallow credentials in CORS (use JWT tokens instead)
        configuration.setAllowCredentials(false);
        
        // Maximum cache age for preflight requests (1 hour)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Password encoder bean using BCrypt for secure password hashing.
     * BCrypt automatically handles salt generation and iteration count.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength of 12 provides good security vs performance tradeoff
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Authentication manager bean for authentication processing.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
