package com.bookmyshow.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.model.MovieAddRequestBody;
import com.bookmyshow.model.MovieViewDetailsReq;
import com.bookmyshow.service.MovieService;

@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addMovie(@RequestBody MovieAddRequestBody movieAddReq){
		return new ResponseEntity<>(movieService.addMovieDetails(movieAddReq),HttpStatus.OK);
	}
	
	@GetMapping("/tabledata")
	public ResponseEntity<?> fetchALLMovie(){
		return new ResponseEntity<>(movieService.fetchAllMovies(),HttpStatus.OK);
	}
	@GetMapping("/landingdata")
	public ResponseEntity<?> fetchLandingPageData(){
		return new ResponseEntity<>(movieService.fetchLandingPageMovies(),HttpStatus.OK);
	}
	
	@PostMapping("/viewDetails")
	public ResponseEntity<?> fetchViewDetailsData(@RequestBody MovieViewDetailsReq requestBody){
		return new ResponseEntity<>(movieService.fetchMovieViewDetaild(requestBody),HttpStatus.OK);
	}
	
}
