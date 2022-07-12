/**
 * 
 */
package com.bookmyshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookmyshow.entity.MovieData;

/**
 * @author jai12
 *
 */
@Repository
public interface MovieRepo extends JpaRepository<MovieData, Long> {

	public List<MovieData> findByMovieId(long Id);
}
