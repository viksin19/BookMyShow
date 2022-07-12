package com.bookmyshow.util;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.bookmyshow.entity.Cast;
import com.bookmyshow.entity.Genre;
import com.bookmyshow.entity.Language;
import com.bookmyshow.entity.MovieData;
import com.bookmyshow.model.Movies;

public class MovieDataMapper {

	public static Function<MovieData, Movies> MovieDataToMovies(){
		return (MovieData movieData)->{
			
			Movies movie = new Movies();
			movie.setMovieId(movieData.getMovieId());
			movie.setMovieName(movieData.getMovieName());
			movie.setMovieLikes(movieData.getMovieLikes());
			movie.setMovieRating(movieData.getMovieRating());
			movie.setMovieDuration(movieData.getMovieDuration());
			movie.setImgUrl(movieData.getMovieUrl());
			movie.setMovieYear(movieData.getYear());
			
			movie.setMovieCast(movieData.getMovieCasts().stream()
					.map(Cast::getCastName)
					.sorted()
					.collect(Collectors.toList()));
			
			movie.setMovieGenre(movieData.getMovieGenre().stream()
					.map(Genre::getGenre)
					.sorted()
					.collect(Collectors.toList()));
			
			movie.setMovieLanguage(movieData.getMovieLanguages().stream()
					.map(Language::getLanguage)
					.sorted()
					.collect(Collectors.toList()));
			
			movie.setMovieLocation(movieData.getMovieLocations().parallelStream()
					.map(LocationMapper.locationEntityToLocation())
					.collect(Collectors.toList()));
			
			return movie;
		};
	}

	public static Function<MovieData, Movies> MovieDataToLandingPageMovies() {
	return (MovieData movieData)->{
			
			Movies movie = new Movies();
			movie.setMovieName(movieData.getMovieName());
			movie.setMovieLikes(movieData.getMovieLikes());
			movie.setMovieRating(movieData.getMovieRating());
			movie.setImgUrl(movieData.getMovieUrl());
			
			movie.setMovieGenre(movieData.getMovieGenre().stream()
					.map(Genre::getGenre)
					.sorted()
					.collect(Collectors.toList()));
			
			return movie;
		};
	}
}
