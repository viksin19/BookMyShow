package com.bookmyshow.util;
import java.util.HashSet;
import java.util.function.Function;
import com.bookmyshow.entity.Location;
import com.bookmyshow.model.ChartData;
import com.bookmyshow.model.MovieLocation;

public class LocationMapper {
	public static Function<Location, MovieLocation> locationEntityToLocation() {
		return (location)->{
			MovieLocation movieLocation = new MovieLocation();
			movieLocation.setLocationId(location.getId());
			movieLocation.setCity(location.getCity());
			movieLocation.setTheatreList(new HashSet<>());
			return movieLocation;
		};
	}

	public static Function<Location, ChartData> locationEntityToLocationChart() {
		return (location)->{
			return new ChartData(location.getCity(), location.getMovies().size());
		};
	}

}
