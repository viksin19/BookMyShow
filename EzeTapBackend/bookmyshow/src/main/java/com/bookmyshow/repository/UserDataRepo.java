package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.UserData;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, String> {

	/**
	 * Find user by userId.
	 * Password verification should be done using PasswordEncoder.
	 */
	public UserData findByUserId(String userId);
	
	/**
	 * @deprecated Use findByUserId and verify password with PasswordEncoder instead.
	 * Passwords should never be compared as plain text.
	 */
	@Deprecated(forRemoval = true)
	public UserData findByUserIdAndUserPassword(String userId, String userPassword);
}
