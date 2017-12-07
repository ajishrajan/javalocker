package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ajishrajan
 * Chicago, IL (Dec 2017)
 * 
 */
public class Product {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("currentPrice")
	private Price currentPrice;
	
	public Product(String id, String name, Price currentPrice) {
		//super();
		this.id = id;
		this.name = name;
		this.currentPrice = currentPrice;
	}
	
	public Product() {
		//dummy constructor
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", currentPrice=" + currentPrice + "]";
	}
	
}
