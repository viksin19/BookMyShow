package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.Cast;

@Repository
public interface CastRepo extends JpaRepository<Cast, Long> {

}
