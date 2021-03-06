package com.bookmyshow.util;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.bookmyshow.entity.Theatre;
import com.bookmyshow.model.MovieTheatre;

public class TheatreMapper {

	public static Function<MovieTheatre, Theatre> movieTheatreToTheatre() {
		
		return (movieTheatre)->{
			Theatre theatre = new Theatre();
			theatre.setTheatreName(movieTheatre.getTheatreName());
			theatre.setTheatreAddress(movieTheatre.getTheatreAddress());
			theatre.setSeatCount(movieTheatre.getSeatCount());
			
			//theatre.setTheatreTimings(movieTheatre.getMovieTimings().stream()
					//.map(TheatreTimingMapper.timingSetToTimingEntity()).collect(Collectors.toSet()));
			
			theatre.setMoviePrice(movieTheatre.getMoviePrice().stream()
					.map(PriceMapper.moviePriceToPriceEntity())
					.collect(Collectors.toSet()));
			return theatre;
		};
	}

}
