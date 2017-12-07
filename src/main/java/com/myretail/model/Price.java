/**
 * 
 */
package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ajishrajan
 * Chicago, IL (Dec, 2017)
 *
 */
public class Price {
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("currencyCode")
	private String currencyCode;
	
	public Price(String value, String currencyCode) {
		//super();
		this.value = value;
		this.currencyCode = currencyCode;
	}
	
	public Price() {
		//dummy constructor
	}

	public String getValue() {
		return value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "Price [value=" + value + ", currencyCode=" + currencyCode + "]";
	}
	
}
