package com.bookmyshow.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Movie view details request model.
 * Contains movieId and locationId for fetching movie details.
 * 
 * @author BookMyShow Dev Team
 */
@JsonSerialize
public class MovieViewDetailsReq {

	@NotNull(message = "Movie ID cannot be null")
	@Positive(message = "Movie ID must be a positive number")
	private long movieId;
	
	@NotNull(message = "Location ID cannot be null")
	@Positive(message = "Location ID must be a positive number")
	private long locationId;
	
	public long getMovieId() {
		return movieId;
	}
	
	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}
	
	public long getLocationId() {
		return locationId;
	}
	
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
}
