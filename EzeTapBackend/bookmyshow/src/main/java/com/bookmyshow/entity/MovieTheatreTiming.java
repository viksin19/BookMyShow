package com.bookmyshow.entity;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
@Entity
public class MovieTheatreTiming {
	@EmbeddedId
	private MovieTheatreTimingId movieTheatreTimingId = new MovieTheatreTimingId();
	
	/**
	 * 
	 */
	public MovieTheatreTiming() {
		super();
	}

	@Transient
	public MovieData getMovies() {
		return movieTheatreTimingId.getMovies();
	}
	
	@Transient
	public Theatre getTheatre() {
		return movieTheatreTimingId.getTheatre();
	}
	
	@Transient
	public Timing getTiming() {
		return movieTheatreTimingId.getTiming();
	}

	public MovieTheatreTimingId getMovieTheatreTimingId() {
		return movieTheatreTimingId;
	}
	
}
