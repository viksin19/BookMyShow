/**
 * 
 */
package com.bookmyshow.serviceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.entity.MovieData;
import com.bookmyshow.entity.Theatre;
import com.bookmyshow.model.MovieAddRequestBody;
import com.bookmyshow.model.MovieLocation;
import com.bookmyshow.model.MovieTheatre;
import com.bookmyshow.model.MovieViewDetailsReq;
import com.bookmyshow.model.Movies;
import com.bookmyshow.repository.MovieRepo;
import com.bookmyshow.service.MovieService;
import com.bookmyshow.util.MovieDataMapper;
import com.bookmyshow.util.PriceMapper;

/**
 * Service implementation for movie management.
 * Handles movie data operations and queries.
 * @author jai12
 *
 */
@Service
public class MovieServiceImpl implements MovieService {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private MovieRepo movieRepo;
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public String addMovieDetails(MovieAddRequestBody movieAddReq) {
		try {
			logger.info("Adding new movie: {}", movieAddReq);
			addMovie(movieAddReq);
			logger.info("Movie added successfully");
		} catch (Exception e) {
			logger.error("Error while saving movie data", e);
			return "Error:: while saving data.";
			
		}
		return "Movie got added successfully.";
	}

	private void addMovie(MovieAddRequestBody movieAddReq) {}

	@Override
	public List<Movies> fetchAllMovies() {
		logger.debug("Fetching all movies");
		List<MovieData> resultList = movieRepo.findAll();
		List<Movies> movieList = resultList.stream()
				.map(MovieDataMapper.MovieDataToMovies())
				.collect(Collectors.toList());
		logger.info("Retrieved {} movies", movieList.size());
		return movieList;
	}

	@Override
	public List<Movies> fetchLandingPageMovies() {
		logger.debug("Fetching landing page movies");
		List<MovieData> resultList = movieRepo.findAll();
		List<Movies> movieList = resultList.stream()
				.map(MovieDataMapper.MovieDataToLandingPageMovies())
				.collect(Collectors.toList());
		logger.info("Retrieved {} movies for landing page", movieList.size());
		return movieList;
	}

	@Override
	public MovieLocation fetchMovieViewDetaild(MovieViewDetailsReq requestBody) {
		logger.debug("Fetching movie details for movieId: {}, locationId: {}", 
			requestBody.getMovieId(), requestBody.getLocationId());
		
		MovieData movieData = movieRepo.findByMovieId(requestBody.getMovieId());
		MovieLocation response = new MovieLocation(); 
		response.setLocationId(requestBody.getLocationId());
		
		if (movieData == null) {
			logger.warn("Movie not found with id: {}", requestBody.getMovieId());
			return response;
		}
		
		movieData.getMovieLocations().stream()
			.filter(location -> location.getId() == requestBody.getLocationId())
			.forEach(location -> {
				response.setCity(location.getCity());
				
				Set<MovieTheatre> theatreList = location.getTheatreList().stream()
					.map((Theatre theatre) -> {
						MovieTheatre movieTheatre = new MovieTheatre();
						movieTheatre.setTheatreId(theatre.getId());
						movieTheatre.setTheatreName(theatre.getTheatreName());
						movieTheatre.setTheatreAddress(theatre.getTheatreAddress());
						movieTheatre.setSeatCount(theatre.getSeatCount());
						movieTheatre.setMoviePrice(theatre.getMoviePrice().stream()
							.map(PriceMapper.priceEntityToMoviePrice())
							.collect(Collectors.toSet()));

						List<String> timings = getTimingByMovieIdAndTheatreId(
							requestBody.getMovieId(), theatre.getId());
						movieTheatre.setMovieTimings(new HashSet<>(timings));
						return movieTheatre;
					})
					.filter(movieTheatre -> !movieTheatre.getMovieTimings().isEmpty())
					.collect(Collectors.toSet());
				response.setTheatreList(theatreList);
				logger.info("Retrieved {} theatres for movie", theatreList.size());
			});
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getTimingByMovieIdAndTheatreId(long movieId, long theatreId) {
		logger.debug("Fetching timings for movieId: {}, theatreId: {}", movieId, theatreId);
		
		String query = "Select Distinct tm.movie_timing from  timing tm, movie_theatre_timing mtt "
			+ "where mtt.movie_id = :movieId AND mtt.theatre_id = :theatreId AND mtt.timing_id = tm.timing_id";
		
		Query sqlquery = entityManager.createNativeQuery(query);
		sqlquery.setParameter("movieId", movieId);
		sqlquery.setParameter("theatreId", theatreId);
		
		List<String> timings = sqlquery.getResultList();
		logger.debug("Retrieved {} timings", timings.size());
		return timings;
	}
}
