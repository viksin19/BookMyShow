package com.bookmyshow.util;

import java.util.function.Function;

import com.bookmyshow.entity.TheatrePrice;
import com.bookmyshow.model.MoviePrice;


public class PriceMapper {
	public static Function<TheatrePrice,MoviePrice > priceEntityToMoviePrice() {
		
		return (price)->{
			MoviePrice moviePrice = new MoviePrice();
			moviePrice.setSeatType(price.getSeatType());
			moviePrice.setSeatPrice(price.getSeatPrice());
			
			return moviePrice;
		};			
	}

}
