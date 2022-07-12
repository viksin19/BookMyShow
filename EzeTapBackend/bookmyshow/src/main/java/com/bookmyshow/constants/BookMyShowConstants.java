package com.bookmyshow.constants;

import java.util.HashMap;
import java.util.Map;

public class BookMyShowConstants {

	public static final Map<String, Long> genreMap = new HashMap<>();
	static {
	genreMap.put("Action",101L);
	genreMap.put("Adventure",102L);
	genreMap.put("Biography",103L);
	genreMap.put("Thriller",104L);
	genreMap.put("Drama",105L);
	genreMap.put("Crime",106L);
	genreMap.put("Suspense",107L);
	genreMap.put("Fantasy",108L);
	genreMap.put("Horror",109L);
	genreMap.put("Comedy",110L);
	genreMap.put("Animation",111L);
	}
}
