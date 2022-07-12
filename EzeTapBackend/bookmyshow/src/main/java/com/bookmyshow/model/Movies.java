package com.bookmyshow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movies {

	private long movieId;
	private String movieName;
	private long movieRating;
	private long movieLikes;
	private String imgUrl;
	private String movieYear;
	private long movieDuration;
	
	private List<String> movieCast;
	private List<String> movieGenre;
	private List<String> movieLanguage;
	
	private List<MovieLocation> movieLocation;

	
	/**
	 * 
	 */
	public Movies() {}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMovieYear() {
		return movieYear;
	}

	public void setMovieYear(String movieYear) {
		this.movieYear = movieYear;
	}

	public long getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(long movieDuration) {
		this.movieDuration = movieDuration;
	}

	public List<String> getMovieCast() {
		return movieCast;
	}

	public void setMovieCast(List<String> movieCast) {
		this.movieCast = movieCast;
	}

	public List<String> getMovieGenre() {
		return movieGenre;
	}

	public void setMovieGenre(List<String> movieGenre) {
		this.movieGenre = movieGenre;
	}

	public List<String> getMovieLanguage() {
		return movieLanguage;
	}

	public void setMovieLanguage(List<String> movieLanguage) {
		this.movieLanguage = movieLanguage;
	}

	public List<MovieLocation> getMovieLocation() {
		return movieLocation;
	}

	public void setMovieLocation(List<MovieLocation> movieLocation) {
		this.movieLocation = movieLocation;
	}

	
	
}
