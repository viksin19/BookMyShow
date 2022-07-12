package com.bookmyshow.model;

import java.util.Set;

public class MovieAddRequestBody {

	
	private Set<Movies> movieList;

	
	/**
	 * 
	 */
	public MovieAddRequestBody() {}

	public Set<Movies> getMovieList() {
		return movieList;
	}

	public void setMovieList(Set<Movies> movieList) {
		this.movieList = movieList;
	}
	
	
}
