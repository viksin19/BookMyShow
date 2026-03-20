package com.bookmyshow.model;

import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Movie add request model.
 * Contains movie data for adding new movies to the system.
 */
public class MovieAddRequestBody {

	@NotNull(message = "Movie list cannot be null")
	@NotEmpty(message = "Movie list cannot be empty")
	private Set<Movies> movieList;

	public MovieAddRequestBody() {}

	public Set<Movies> getMovieList() {
		return movieList;
	}

	public void setMovieList(Set<Movies> movieList) {
		this.movieList = movieList;
	}
}
