package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.Language;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {

}
