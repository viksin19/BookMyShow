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
 * @author jai12
 *
 */
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepo movieRepo;
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public String addMovieDetails(MovieAddRequestBody movieAddReq) {
		try {
			addMovie(movieAddReq);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error:: while saving data.";
			
		}
		return "Movie got added successfully.";
	}

	private void addMovie(MovieAddRequestBody movieAddReq) {}

	@Override
	public List<Movies> fetchAllMovies() {
		List<MovieData> resultList = movieRepo.findAll();
		List<Movies> movieList = resultList.stream()
				.map(MovieDataMapper.MovieDataToMovies())
				.collect(Collectors.toList());
		
		return movieList;
	}

	@Override
	public List<Movies> fetchLandingPageMovies() {
		List<MovieData> resultList = movieRepo.findAll();
		List<Movies> movieList = resultList.stream()
				.map(MovieDataMapper.MovieDataToLandingPageMovies())
				.collect(Collectors.toList());
		
		return movieList;
	}

	@Override
	public MovieLocation fetchMovieViewDetaild(MovieViewDetailsReq requestBody) {
	  MovieData movieData = movieRepo.findByMovieId(requestBody.getMovieId());
	  MovieLocation response = new MovieLocation(); 
	  response.setLocationId(requestBody.getLocationId());
	  
		  movieData.getMovieLocations().stream().filter(location-> location.getId()==requestBody.getLocationId()).forEach(location->{
			  response.setCity(location.getCity());
			  
			  Set<MovieTheatre> theatreList = location.getTheatreList().stream().map((Theatre theatre)->{
						  MovieTheatre movieTheatre = new MovieTheatre();
						  movieTheatre.setTheatreId(theatre.getId());
						  movieTheatre.setTheatreName(theatre.getTheatreName());
						  movieTheatre.setTheatreAddress(theatre.getTheatreAddress());
						  movieTheatre.setSeatCount(theatre.getSeatCount());
						  movieTheatre.setMoviePrice(theatre.getMoviePrice().stream()
								  .map(PriceMapper.priceEntityToMoviePrice())
								  .collect(Collectors.toSet()));

						 List<String> timings = getTimingByMovieIdAndTheatreId(requestBody.getMovieId(),theatre.getId());
						 movieTheatre.setMovieTimings(new HashSet<>(timings));
						  return movieTheatre;
					  }).filter(movieTheatre->!movieTheatre.getMovieTimings().isEmpty())
					  .collect(Collectors.toSet());
                response.setTheatreList(theatreList);
		  });
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getTimingByMovieIdAndTheatreId(long movieId,long theatreId){
		  String query = "Select Distinct tm.movie_timing from  timing tm, movie_theatre_timing mtt "
			  		+ "where mtt.movie_id = :movieId AND mtt.theatre_id = :theatreId AND mtt.timing_id = tm.timing_id";
			 Query sqlquery =  entityManager.createNativeQuery(query);
			 sqlquery.setParameter("movieId", movieId);
			 sqlquery.setParameter("theatreId", theatreId);
			return sqlquery.getResultList();
	}
}
