package com.bookmyshow.model;

import java.util.Set;

public class MovieListResponse {

	private Set<Movies> movieList;

	public Set<Movies> getMovieList() {
		return movieList;
	}

	public void setMovieList(Set<Movies> movieList) {
		this.movieList = movieList;
	}
	
}
