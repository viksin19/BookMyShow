package com.bookmyshow.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "Genre")
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "genre_id")
	private long Id;
	
	@Column(name = "genre")
	private String genre;

	@ManyToMany(mappedBy = "movieGenre")
	private Set<MovieData> movies;
	
	public Genre() {}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Set<MovieData> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieData> movies) {
		this.movies = movies;
	}

}
