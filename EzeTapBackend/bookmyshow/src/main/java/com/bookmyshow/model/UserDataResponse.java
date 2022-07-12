package com.bookmyshow.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UserDataResponse {

	private String userId;
	private String userName;
	private String userRole;
	private String emailId;
	
	
	/**
	 * @param userId
	 * @param userName
	 * @param userRole
	 * @param emailId
	 */
	public UserDataResponse(String userId, String userName, String userRole, String emailId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userRole = userRole;
		this.emailId = emailId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
