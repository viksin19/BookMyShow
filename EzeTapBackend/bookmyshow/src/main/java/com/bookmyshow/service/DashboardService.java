package com.bookmyshow.service;

import com.bookmyshow.model.ChartDataResponse;
import com.bookmyshow.model.DashboardCardResponse;

public interface DashboardService {

	public DashboardCardResponse getGenreCardData();
	public ChartDataResponse getGenreChartData();
	
	public DashboardCardResponse getLanguageCardData();
	
	public DashboardCardResponse getLocationCardData();
	public ChartDataResponse getLocationChartData();
	
	public DashboardCardResponse getMoviesCardData();
}
