package com.bookmyshow.serviceImpl;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.entity.Genre;
import com.bookmyshow.entity.Language;
import com.bookmyshow.entity.Location;
import com.bookmyshow.entity.MovieData;
import com.bookmyshow.model.ChartData;
import com.bookmyshow.model.ChartDataResponse;
import com.bookmyshow.model.DashboardCardResponse;
import com.bookmyshow.repository.GenreRepo;
import com.bookmyshow.repository.LanguageRepo;
import com.bookmyshow.repository.LocationRepo;
import com.bookmyshow.repository.MovieRepo;
import com.bookmyshow.service.DashboardService;
import com.bookmyshow.util.GenreMapper;
import com.bookmyshow.util.LocationMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private GenreRepo genreRepo;
	
	@Autowired
	private LocationRepo locationRepo;
	
	@Autowired
	private LanguageRepo languageRepo;
	
	@Autowired
	private MovieRepo movieRepo;
	
	@Override
	public DashboardCardResponse getGenreCardData() {
		List<String> cardSubTitleList = genreRepo.findAll().stream()
				.map(Genre::getGenre)
				.sorted()
				.collect(Collectors.toList());
		return prepareCardData(cardSubTitleList);
	}

	@Override
	public DashboardCardResponse getLanguageCardData() {
		List<String> cardSubTitleList = languageRepo.findAll().stream()
				.map(Language::getLanguage)
				.sorted()
				.collect(Collectors.toList());

		return prepareCardData(cardSubTitleList);
	}

	@Override
	public DashboardCardResponse getLocationCardData() {
		List<String> cardSubTitleList = locationRepo.findAll().stream()
				.map(Location::getCity)
				.sorted()
				.collect(Collectors.toList());

		return prepareCardData(cardSubTitleList);
	}

	@Override
	public DashboardCardResponse getMoviesCardData() {
		List<String> cardSubTitleList = movieRepo.findAll().stream()
				.map(MovieData::getMovieName)
				.sorted()
				.collect(Collectors.toList());

		return prepareCardData(cardSubTitleList);
	}

	private DashboardCardResponse prepareCardData(List<String> cardSubTitleList) {
		DashboardCardResponse cardResponse = new DashboardCardResponse();
		cardResponse.setCardValue(cardSubTitleList.size());
		cardResponse.setSubTitle(cardSubTitleList);
		return cardResponse;
	}

	@Override
	public ChartDataResponse getGenreChartData() {
		ChartDataResponse chartResponse = new ChartDataResponse();
		List<ChartData> chartDataList =  genreRepo.findAll().stream()
				.map(GenreMapper.genreEntityToGenreChart())
				.collect(Collectors.toList());
		chartResponse.setChartDataList(chartDataList);
		return chartResponse;
	}

	@Override
	public ChartDataResponse getLocationChartData() {
		ChartDataResponse chartResponse = new ChartDataResponse();
		List<ChartData> chartDataList =  locationRepo.findAll().stream()
				.map(LocationMapper.locationEntityToLocationChart())
				.collect(Collectors.toList());
		chartResponse.setChartDataList(chartDataList);
		return chartResponse;
	}
}
