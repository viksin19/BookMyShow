package com.bookmyshow.model;

import java.util.Set;

public class MovieTheatre {

	private long theatreId;
	private String theatreName;
	private String theatreAddress;
	private long seatCount;
	
	private Set<String> movieTimings;
	private Set<MoviePrice> moviePrice;
	
	
	public MovieTheatre() {}
	
	
	public long getTheatreId() {
		return theatreId;
	}
	public void setTheatreId(long theatreId) {
		this.theatreId = theatreId;
	}
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public String getTheatreAddress() {
		return theatreAddress;
	}
	public void setTheatreAddress(String theatreAddress) {
		this.theatreAddress = theatreAddress;
	}
	public long getSeatCount() {
		return seatCount;
	}
	public void setSeatCount(long seatCount) {
		this.seatCount = seatCount;
	}
	public Set<String> getMovieTimings() {
		return movieTimings;
	}
	public void setMovieTimings(Set<String> movieTimings) {
		this.movieTimings = movieTimings;
	}
	public Set<MoviePrice> getMoviePrice() {
		return moviePrice;
	}
	public void setMoviePrice(Set<MoviePrice> moviePrice) {
		this.moviePrice = moviePrice;
	}
	
	
	
}
