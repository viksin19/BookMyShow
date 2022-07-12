/**
 * 
 */
package com.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jai12
 *
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

	private String errorMessage;

	/**
	 * @param errorMessage
	 */
	public ErrorResponse(String errorMessage) {
		//super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
