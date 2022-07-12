/**
 * 
 */
package com.bookmyshow.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jai12
 *
 */
@JsonSerialize
public class MovieViewDetailsReq {

	private long movieId;
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
