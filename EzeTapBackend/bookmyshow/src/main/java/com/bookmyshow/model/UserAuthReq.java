package com.bookmyshow.model;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * User authentication request model.
 * Contains credentials for user authentication.
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "User ID cannot be empty")
	@Size(min = 3, max = 50, message = "User ID must be between 3 and 50 characters")
	private String userId;
	
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String userPassword;

	public UserAuthReq(String userId, String userPassword) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
	}
	
	public UserAuthReq() {
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

	
}
