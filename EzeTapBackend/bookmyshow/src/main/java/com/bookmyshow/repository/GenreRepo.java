/**
 * 
 */
package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.Genre;

/**
 * @author jai12
 *
 */
@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {

}
