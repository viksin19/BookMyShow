package com.bookmyshow.service;

import org.springframework.http.ResponseEntity;

import com.bookmyshow.model.UserAuthReq;

public interface UserAuthService {

	public ResponseEntity<?> authenticateUser(UserAuthReq userInfo);
}
