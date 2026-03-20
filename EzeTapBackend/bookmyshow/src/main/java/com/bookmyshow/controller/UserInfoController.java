/**
 * REST Controller for user authentication and information management.
 * Provides secure endpoints for user authentication and profile management.
 */
package com.bookmyshow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.bookmyshow.model.UserAuthReq;
import com.bookmyshow.service.UserAuthService;

/**
 * User authentication controller.
 * Handles user sign-in, registration, and account management.
 * 
 * @author BookMyShow Security Team
 * @version 2.0
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	private UserAuthService userAuthService;
	
	/**
	 * Health check endpoint for service availability.
	 * 
	 * @return Service status
	 */
	@GetMapping("/check")
	public ResponseEntity<?> checkService(){
		logger.info("Service health check called");
		return new ResponseEntity<>("bookmyshow service is working.", HttpStatus.OK);
	}
	
	/**
	 * User authentication endpoint.
	 * Validates credentials and returns JWT token on successful authentication.
	 * 
	 * @param userInfo User authentication request containing userId and password
	 * @return User info with token on success, error message on failure
	 */
	@PostMapping("/auth")
	public ResponseEntity<?> userAuth(@Valid @RequestBody UserAuthReq userInfo){
		logger.info("User authentication attempt for user: {}", sanitizeUserId(userInfo.getUserId()));
		
		try {
			ResponseEntity<?> response = userAuthService.authenticateUser(userInfo);
			if (response.getStatusCode() == HttpStatus.OK) {
				logger.info("User authentication successful for user: {}", sanitizeUserId(userInfo.getUserId()));
			} else {
				logger.warn("Failed authentication attempt for user: {}", sanitizeUserId(userInfo.getUserId()));
			}
			return response;
		} catch (Exception e) {
			logger.error("Error during authentication: {}", e.getMessage());
			return new ResponseEntity<>(
				"Authentication failed. Please try again later.",
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}
	
	/**
	 * Sanitize user ID for logging (remove sensitive data).
	 */
	private String sanitizeUserId(String userId) {
		if (userId == null || userId.length() < 3) {
			return "[REDACTED]";
		}
		return userId.substring(0, Math.min(3, userId.length())) + "***";
	}
}
