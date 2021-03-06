/**
 * 
 */
package com.bookmyshow.entity;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jai12
 *
 */
@Entity
@Table(name="Movie")
@JsonSerialize
public class MovieData {

	@Id
	@Column(name="movie_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long movieId;
	
	@Column(name = "movie_name")
	private String movieName;
	
	@Column(name = "movie_rating")
	private long movieRating;
	
	@Column(name = "movie_likes")
	private long movieLikes;
	
	@Column(name = "movie_duration")
	private long movieDuration;
	
	@Column(name = "movie_url")
	private String movieUrl;
	
	
	@Column(name = "movie_year")
	private String year;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "movie_cast",
	joinColumns = @JoinColumn(name = "movie_id"),
	inverseJoinColumns = @JoinColumn(name = "cast_id"))
	private Set<Cast> movieCasts;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "movie_language",
	joinColumns = @JoinColumn(name = "movie_id"),
	inverseJoinColumns = @JoinColumn(name = "language_id"))
	private Set<Language> movieLanguages;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "movie_location",
	joinColumns = @JoinColumn(name = "movie_id"),
	inverseJoinColumns = @JoinColumn(name = "location_id"))
	private Set<Location> movieLocations;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "movie_genre",
	joinColumns = @JoinColumn(name = "movie_id"),
	inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> movieGenre;
	
	@OneToMany(mappedBy = "movieTheatreTimingId.movies")
	private Set<MovieTheatreTiming> theatreTimingList;
	/**
	 * 
	 */
	public MovieData() {}


	public long getMovieId() {
		return movieId;
	}


	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}


	public String getMovieName() {
		return movieName;
	}


	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}


	public Set<Cast> getMovieCasts() {
		return movieCasts;
	}


	public void setMovieCasts(Set<Cast> movieCasts) {
		this.movieCasts = movieCasts;
	}


	public Set<Language> getMovieLanguages() {
		return movieLanguages;
	}


	public void setMovieLanguages(Set<Language> movieLanguages) {
		this.movieLanguages = movieLanguages;
	}


	public Set<Location> getMovieLocations() {
		return movieLocations;
	}


	public void setMovieLocations(Set<Location> movieLocations) {
		this.movieLocations = movieLocations;
	}


	public Set<Genre> getMovieGenre() {
		return movieGenre;
	}


	public void setMovieGenre(Set<Genre> movieGenre) {
		this.movieGenre = movieGenre;
	}

	public long getMovieRating() {
		return movieRating;
	}


	public void setMovieRating(long movieRating) {
		this.movieRating = movieRating;
	}


	public long getMovieLikes() {
		return movieLikes;
	}


	public void setMovieLikes(long movieLikes) {
		this.movieLikes = movieLikes;
	}


	public String getMovieUrl() {
		return movieUrl;
	}


	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public long getMovieDuration() {
		return movieDuration;
	}


	public void setMovieDuration(long movieDuration) {
		this.movieDuration = movieDuration;
	}


	public Set<MovieTheatreTiming> getTheatreTimingList() {
		return theatreTimingList;
	}


	public void setTheatreTimingList(Set<MovieTheatreTiming> theatreTimingList) {
		this.theatreTimingList = theatreTimingList;
	}
	
	}
