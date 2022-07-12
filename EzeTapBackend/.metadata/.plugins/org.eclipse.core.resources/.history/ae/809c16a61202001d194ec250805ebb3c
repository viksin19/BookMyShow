package com.bookmyshow.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "language_id")
	private long id;
	
	@Column(name = "language")
	private String language;
	
	@ManyToMany(mappedBy = "movieLanguages")
	private Set<MovieData> movies;

	
	/**
	 * 
	 */
	public Language() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Set<MovieData> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieData> movies) {
		this.movies = movies;
	}
	
}
