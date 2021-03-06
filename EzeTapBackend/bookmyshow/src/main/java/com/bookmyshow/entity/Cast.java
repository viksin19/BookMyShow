package com.bookmyshow.entity;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Cast")
public class Cast {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cast_id")
	private long id;
	
	@Column(name = "cast_name")
	private String castName;
	
	@ManyToMany(mappedBy = "movieCasts")
	private Set<MovieData> castMovies;

	
	
	/**
	 * Default constructor
	 */
	public Cast() {}

	public long getId() {
		return id;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public Set<MovieData> getCastMovies() {
		return castMovies;
	}

	public void setCastMovies(Set<MovieData> castMovies) {
		this.castMovies = castMovies;
	}
	
	
	
	
	
}
