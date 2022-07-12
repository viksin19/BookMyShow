/**
 * 
 */
package com.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookmyshow.entity.MovieData;

/**
 * @author jai12
 *
 */
@Repository
public interface MovieRepo extends JpaRepository<MovieData, Long> {

	public MovieData findByMovieId(long Id);
}
