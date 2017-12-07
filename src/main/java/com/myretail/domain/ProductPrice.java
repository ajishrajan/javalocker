package com.myretail.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"id", "revision"})

public class ProductPrice {
	
	@JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String revision;
    
    @JsonProperty("product_id")
    private String productId;
    
    @JsonProperty("product_name")
    private String productName;
    
    @JsonProperty("price")
    private String price;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

    
}
