/**
 * 
 */
package com.myretail.util;

import org.springframework.stereotype.Service;

/**
 * @author ajishrajan
 * Chicago, IL (Dec 2017)
 *
 */

@Service
public class Properties {
	
	private final String REDSKY_API_URI = "http://redsky.target.com/v2/pdp/tcin/" ;
	private final String COUCH_DB_URL = "http://localhost:5984" ;
	private final String COUCH_DB_NAME = "product_price";
	
	public String getExternalApiUri() {
		return REDSKY_API_URI.toString();
	}
	
	public String getCouchDbUrl() {
		return COUCH_DB_URL.toString();
	}
	
	public String getDatabaseName() {
		return COUCH_DB_NAME.toString();
	}
}
