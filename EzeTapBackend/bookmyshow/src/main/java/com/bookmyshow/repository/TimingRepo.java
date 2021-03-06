package com.bookmyshow.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookmyshow.entity.Timing;

@Repository
public interface TimingRepo extends JpaRepository<Timing, Long> {

//	@Query("select t.movieTiming from Timing t Join t.movieTheatreList tm on tm.timing.timingId = t.timingId Where tm.movies.movieId = :movieId"
//			+ " AND tm.theatre.Id = :theatreId ")
//	public Set<String> findByMovieIdAndTheatreId(long movieId,long theatreId);
}
