/**
 * 
 */
package com.bookmyshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.model.UserAuthReq;
import com.bookmyshow.service.UserAuthService;

/**
 * @author jai12
 *
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserInfoController {
	
	@Autowired
	private UserAuthService userAuthService;
	@GetMapping("/check")
	public ResponseEntity<?> checkService(){
		return new ResponseEntity<>("bookmyshow service is working.",HttpStatus.OK);
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> userAuth(@RequestBody UserAuthReq userInfo){
		return userAuthService.authenticateUser(userInfo);
	}

}
