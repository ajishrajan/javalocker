package com.myretail.domain;

import com.myretail.model.Price;

/**
 * @author ajishrajan
 * Chicago, IL (Dec 2017)
 *
 */

public interface ProductDetailService {
	
	public String getProductName(String id);
	public Price getProductPrice(String id);
	public int updateProductPrice(String id, Price price);
}
