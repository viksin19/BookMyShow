package com.bookmyshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashBoardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/genre")
	public ResponseEntity<?> fetchGenreCardData(){
		return new ResponseEntity<>(dashboardService.getGenreCardData(),HttpStatus.OK);
	}
	
	@GetMapping("/genrechart")
	public ResponseEntity<?> fetchGenreChartData(){
		return new ResponseEntity<>(dashboardService.getGenreChartData(),HttpStatus.OK);
	}
	
	@GetMapping("/locations")
	public ResponseEntity<?> fetchLocationCardData(){
		return new ResponseEntity<>(dashboardService.getLocationCardData(),HttpStatus.OK);
	}
	
	@GetMapping("/locationchart")
	public ResponseEntity<?> fetchLocationChartData(){
		return new ResponseEntity<>(dashboardService.getLocationChartData(),HttpStatus.OK);
	}
	
	@GetMapping("/languages")
	public ResponseEntity<?> fetchLanguageCardData(){
		return new ResponseEntity<>(dashboardService.getLanguageCardData(),HttpStatus.OK);
	}
	
	@GetMapping("/movies")
	public ResponseEntity<?> fetchMoviesCardData(){
		return new ResponseEntity<>(dashboardService.getMoviesCardData(),HttpStatus.OK);
	}
}
