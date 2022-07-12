package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.UserData;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, String> {

	public UserData findByUserIdAndUserPassword(String userId,String userPassword);
}
