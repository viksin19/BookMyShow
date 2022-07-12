package com.bookmyshow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_data")
public class UserData implements Serializable{

	private static final long serialVersionUID = -4386050397683111517L;

	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_password")
	private String userPassword;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_role")
	private String userRole;
	
	@Column(name = "email_id")
	private String emailId;
	
	
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
	

