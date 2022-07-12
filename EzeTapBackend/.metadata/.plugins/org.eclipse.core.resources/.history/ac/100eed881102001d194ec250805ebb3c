package com.bookmyshow.util;

import java.util.function.Function;

import com.bookmyshow.entity.Genre;
import com.bookmyshow.model.ChartData;

public class GenreMapper {

	public static Function<String, Genre> genreNameToGenreEntity() {
		
		return (String genreName)->{
			Genre genre = new Genre();
			genre.setGenre(genreName);
			return genre;
		};
	}

	public static Function<Genre, String> genreEntityToGenreName() {
		return genre->genre.getGenre();
	}

	public static Function<Genre, ChartData> genreEntityToGenreChart() {
		return (genre)->{
			return new ChartData(genre.getGenre(), genre.getMovies().size());
		};
	}

}
