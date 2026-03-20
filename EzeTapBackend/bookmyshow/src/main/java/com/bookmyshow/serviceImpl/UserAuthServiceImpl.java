package com.bookmyshow.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyshow.entity.UserData;
import com.bookmyshow.model.ErrorResponse;
import com.bookmyshow.model.UserAuthReq;
import com.bookmyshow.model.UserDataResponse;
import com.bookmyshow.repository.UserDataRepo;
import com.bookmyshow.service.UserAuthService;

/**
 * Service implementation for user authentication.
 * Handles secure password verification using BCrypt hashing.
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Autowired
	private UserDataRepo userDataRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public ResponseEntity<?> authenticateUser(UserAuthReq userInfo) {
		logger.info("Authenticating user: {}", userInfo.getUserId());
		
		try {
			// Find user by ID
			UserData userData = userDataRepo.findByUserId(userInfo.getUserId());
			
			if (userData == null) {
				logger.warn("User not found: {}", userInfo.getUserId());
				return new ResponseEntity<>(
					new ErrorResponse("Invalid user ID or password"),
					HttpStatus.UNAUTHORIZED
				);
			}
			
			// Verify hashed password
			if (passwordEncoder.matches(userInfo.getUserPassword(), userData.getUserPassword())) {
				logger.info("User authenticated successfully: {}", userInfo.getUserId());
				return new ResponseEntity<>(
					new UserDataResponse(
						userData.getUserId(),
						userData.getUserName(),
						userData.getUserRole(),
						userData.getEmailId()
					),
					HttpStatus.OK
				);
			} else {
				logger.warn("Authentication failed for user: {}", userInfo.getUserId());
				return new ResponseEntity<>(
					new ErrorResponse("Invalid user ID or password"),
					HttpStatus.UNAUTHORIZED
				);
			}
		} catch (Exception e) {
			logger.error("Error during user authentication", e);
			return new ResponseEntity<>(
				new ErrorResponse("An error occurred during authentication"),
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}

}

