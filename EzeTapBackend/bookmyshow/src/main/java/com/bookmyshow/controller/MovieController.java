package com.bookmyshow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.bookmyshow.model.MovieAddRequestBody;
import com.bookmyshow.model.MovieViewDetailsReq;
import com.bookmyshow.service.MovieService;

/**
 * REST Controller for movie management and retrieval.
 * Provides endpoints for fetching movie data and managing movie information.
 * 
 * @author BookMyShow Dev Team
 * @version 2.0
 */
@RestController
@RequestMapping("/movie")
@Validated
public class MovieController {

	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private MovieService movieService;
	
	/**
	 * Add a new movie to the system.
	 * Restricted to ADMIN role only.
	 * 
	 * @param movieAddReq Movie details to add
	 * @return Response with success/failure status
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addMovie(@Valid @RequestBody MovieAddRequestBody movieAddReq){
		logger.info("Adding new movie: {}", movieAddReq.getMovieName());
		
		try {
			ResponseEntity<?> response = new ResponseEntity<>(
				movieService.addMovieDetails(movieAddReq),
				HttpStatus.CREATED
			);
			logger.info("Movie added successfully: {}", movieAddReq.getMovieName());
			return response;
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid movie data: {}", e.getMessage());
			return new ResponseEntity<>("Invalid movie data provided", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error adding movie: {}", e.getMessage());
			return new ResponseEntity<>("Failed to add movie", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Fetch all movies for admin dashboard/table view.
	 * 
	 * @return List of all movies
	 */
	@GetMapping("/tabledata")
	public ResponseEntity<?> fetchALLMovie(){
		logger.info("Fetching all movies for table view");
		
		try {
			ResponseEntity<?> response = new ResponseEntity<>(
				movieService.fetchAllMovies(),
				HttpStatus.OK
			);
			logger.debug("Successfully fetched all movies");
			return response;
		} catch (Exception e) {
			logger.error("Error fetching movies: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch movies", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Fetch movies for landing page display.
	 * Returns limited/featured movies for public view.
	 * 
	 * @return List of featured movies
	 */
	@GetMapping("/landingdata")
	public ResponseEntity<?> fetchLandingPageData(){
		logger.info("Fetching landing page movies");
		
		try {
			ResponseEntity<?> response = new ResponseEntity<>(
				movieService.fetchLandingPageMovies(),
				HttpStatus.OK
			);
			logger.debug("Successfully fetched landing page movies");
			return response;
		} catch (Exception e) {
			logger.error("Error fetching landing page movies: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch movies", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Fetch detailed information for a specific movie.
	 * Includes pricing, timings, and availability information.
	 * 
	 * @param requestBody Movie details request
	 * @return Detailed movie information
	 */
	@PostMapping("/viewDetails")
	public ResponseEntity<?> fetchViewDetailsData(@Valid @RequestBody MovieViewDetailsReq requestBody){
		logger.info("Fetching movie details for movie ID: {}", requestBody.getMovieId());
		
		try {
			ResponseEntity<?> response = new ResponseEntity<>(
				movieService.fetchMovieViewDetaild(requestBody),
				HttpStatus.OK
			);
			logger.debug("Successfully fetched movie details");
			return response;
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid movie ID: {}", e.getMessage());
			return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error fetching movie details: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch movie details", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
