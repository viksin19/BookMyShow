package com.bookmyshow.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MovieTheatreTimingId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "movie_id")
	private MovieData movies;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "timing_id")
	private Timing timing;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "theatre_id")
	private Theatre theatre;

	
	
	/**
	 * 
	 */
	public MovieTheatreTimingId() {
		super();
	}

	public MovieData getMovies() {
		return movies;
	}

	public void setMovies(MovieData movies) {
		this.movies = movies;
	}

	public Timing getTiming() {
		return timing;
	}

	public void setTiming(Timing timing) {
		this.timing = timing;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	
	
}
