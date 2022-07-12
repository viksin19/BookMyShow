package com.bookmyshow.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DashboardCardResponse {

	private int cardValue;
	private List<String> subTitle;

	public int getCardValue() {
		return cardValue;
	}
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}
	public List<String> getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(List<String> subTitle) {
		this.subTitle = subTitle;
	}
	
	
}
