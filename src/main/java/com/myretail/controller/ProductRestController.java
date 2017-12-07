/**
 * 
 */
package com.myretail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.domain.ProductDetailService;
import com.myretail.model.Price;
import com.myretail.model.Product;

/**
 * @author ajishrajan Chicago, IL (Dec 2017)
 * 
 */

@RestController
@RequestMapping("/products")
@SpringBootApplication
@ComponentScan({ "com.myretail.service", "com.myretail.dao", "com.myretail.util" })
public class ProductRestController {

	private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private ProductDetailService prdService;

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Product getProduct(@PathVariable String id) {
		String name = "";
		Product prd = null;

		// invoking service to call External API to retrieve the product name
		logger.info("invoking service to call External API to retrieve the product name");
		name = prdService.getProductName(id);
		if (name == null) {
			name = "";
			logger.debug("prdService.getProductName returned null");
		}

		// Making call to couchDb to retrieve the product price
		logger.info("Making call to couchDb to retrieve the product price");
		Price currPrice = prdService.getProductPrice(id);
		if (currPrice == null) {
			currPrice = new Price("", "");
			logger.debug("prdService.getProductPrice returned null");
		}
		prd = new Product(id, name, currPrice);
		return prd;
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public HttpStatus updateProduct(@PathVariable String id, @RequestBody Product prd) {

		int success = 0;
		// Making call to update couchDb with given price
		logger.info("Making call to update couchDb with given price");
		if (prd != null) {
			success = prdService.updateProductPrice(id, prd.getCurrentPrice());
		}
		if (success == 1) {
			logger.info("succesfully updated the document with id = " + id);
			return HttpStatus.OK;
		} else {
			logger.info("No updates to the document with id = " + id);
			return HttpStatus.OK;
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ProductRestController.class, args);
	}
}
