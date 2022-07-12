package com.bookmyshow.model;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class MovieLocation {

	private long locationId;
	private String city;
	
	private Set<MovieTheatre> theatreList;

	
	
	/**
	 * 
	 */
	public MovieLocation() {}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<MovieTheatre> getTheatreList() {
		return theatreList;
	}

	public void setTheatreList(Set<MovieTheatre> theatreList) {
		this.theatreList = theatreList;
	}
	
	
}
