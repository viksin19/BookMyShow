package com.bookmyshow.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ChartDataResponse {

	List<ChartData> chartDataList;

	
	
	/**
	 * @param chartDataList
	 */
	public ChartDataResponse(List<ChartData> chartDataList) {
		//super();
		this.chartDataList = chartDataList;
	}

	public ChartDataResponse() {
	}

	public List<ChartData> getChartDataList() {
		return chartDataList;
	}

	public void setChartDataList(List<ChartData> chartDataList) {
		this.chartDataList = chartDataList;
	}
	
	
}
