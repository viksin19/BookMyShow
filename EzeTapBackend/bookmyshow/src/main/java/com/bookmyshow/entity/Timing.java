/**
 * 
 */
package com.bookmyshow.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author jai12
 *
 */
@Entity
@Table(name = "Timing")
public class Timing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "timing_id")
	private long Id;
	
	@Column(name = "movie_timing")
	private String movieTiming;
	
	@OneToMany(mappedBy = "movieTheatreTimingId.timing")
	private Set<MovieTheatreTiming> movieTheatreList;
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getMovieTiming() {
		return movieTiming;
	}

	public void setMovieTiming(String movieTiming) {
		this.movieTiming = movieTiming;
	}

	public Set<MovieTheatreTiming> getMovieTheatreList() {
		return movieTheatreList;
	}

	public void setMovieTheatreList(Set<MovieTheatreTiming> movieTheatreList) {
		this.movieTheatreList = movieTheatreList;
	}
	
	
}
