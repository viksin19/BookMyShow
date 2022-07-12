package com.bookmyshow.model;

public class ChartData {

	private String chartLable;
	private int chartValue;
	
	
	/**
	 * @param chartLable
	 * @param chartValue
	 */
	public ChartData(String chartLable, int chartValue) {
		this.chartLable = chartLable;
		this.chartValue = chartValue;
	}
	
	public String getChartLable() {
		return chartLable;
	}
	public void setChartLable(String chartLable) {
		this.chartLable = chartLable;
	}
	public int getChartValue() {
		return chartValue;
	}
	public void setChartValue(int chartValue) {
		this.chartValue = chartValue;
	}
	
	
}
