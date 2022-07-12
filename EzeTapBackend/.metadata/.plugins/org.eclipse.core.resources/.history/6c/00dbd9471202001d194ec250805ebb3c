package com.bookmyshow.util;

import java.util.function.Function;

import com.bookmyshow.entity.Timing;

public class TheatreTimingMapper {

	public static Function<String, Timing> timingSetToTimingEntity() {
		return (timing)->{
			Timing movieTiming = new Timing();
			movieTiming.setMovieTiming(timing);
			return movieTiming;
		};
	}

}
