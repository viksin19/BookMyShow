package com.bookmyshow.entity;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "Location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "location_id")
	private long Id;
	
	@Column(name = "city")
	private String city;
	
	@ManyToMany(mappedBy = "movieLocations")
	private Set<MovieData> movies;
	
	@OneToMany(mappedBy = "location",cascade = CascadeType.PERSIST)
	private Set<Theatre> theatreList;
	
	/**
	 * 
	 */
	public Location() {
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<MovieData> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieData> movies) {
		this.movies = movies;
	}

	public Set<Theatre> getTheatreList() {
		return theatreList;
	}

	public void setTheatreList(Set<Theatre> theatreList) {
		this.theatreList = theatreList;
	}
	
	
}
