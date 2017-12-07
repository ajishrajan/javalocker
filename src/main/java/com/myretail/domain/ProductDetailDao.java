/**
 * 
 */
package com.myretail.domain;

import java.net.MalformedURLException;

import com.myretail.model.Price;

/**
 * @author ajishrajan
 * Chicago, IL (Dec 2017)
 *
 */

public interface ProductDetailDao {
	public ProductPrice getProductPrice(String id) throws MalformedURLException;
	public int updateProductPrice(String id, Price price) throws MalformedURLException;
}
