package com.bookmyshow.util;

import java.util.function.Function;

import com.bookmyshow.entity.Cast;

public class CastMapper {
	public static Function<String, Cast> castNameToCastEntity(){
		return (String castName)->{
			Cast cast = new Cast();
			cast.setCastName(castName);
			return cast;
		};
	}
}
