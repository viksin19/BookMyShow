package com.bookmyshow.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmyshow.entity.UserData;
import com.bookmyshow.model.ErrorResponse;
import com.bookmyshow.model.UserAuthReq;
import com.bookmyshow.model.UserDataResponse;
import com.bookmyshow.repository.UserDataRepo;
import com.bookmyshow.service.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserDataRepo userDataRepo;
	@Override
	public ResponseEntity<?> authenticateUser(UserAuthReq userInfo) {
		UserData userData = userDataRepo.findByUserIdAndUserPassword(userInfo.getUserId(), userInfo.getUserPassword());
		if(userData!=null)
			return new ResponseEntity<>(new UserDataResponse(userData.getUserId(), userData.getUserName(),
					userData.getUserRole(), userData.getEmailId()) ,HttpStatus.OK);
		
		return new ResponseEntity<>(new ErrorResponse("Error in Auth!!"),HttpStatus.BAD_REQUEST);
	}

}
