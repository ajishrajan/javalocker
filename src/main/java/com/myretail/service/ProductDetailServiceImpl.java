/**
 * 
 */
package com.myretail.service;

import java.net.MalformedURLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.myretail.domain.ProductDetailDao;
import com.myretail.domain.ProductDetailService;
import com.myretail.domain.ProductPrice;
import com.myretail.model.Price;
import com.myretail.util.Properties;

/**
 * @author ajishrajan Chicago, IL (Dec 2017)
 *
 */
@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	private static final Logger logger = LoggerFactory.getLogger(ProductDetailServiceImpl.class);

	@Autowired
	private ProductDetailDao prdDetailDao;

	@Autowired
	private Properties properties;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getProductName(String id) {
		try {
			String url = properties.getExternalApiUri() + id;
			logger.debug("External API url is" + url);
			String prdTitle = "";
			String jsonString = restTemplate.getForObject(url, String.class);
			logger.debug("jsonString" + jsonString);
			JacksonJsonParser parse = new JacksonJsonParser();
			Map<String, Object> jsonMap = parse.parseMap(jsonString);
			Map<String, Object> productMap = (jsonMap != null) ? (Map<String, Object>) jsonMap.get("product") : null;
			Map<String, Object> itemMap = (productMap != null) ? (Map<String, Object>) productMap.get("item") : null;
			Map<String, Object> prodDescMap = (itemMap != null)
					? (Map<String, Object>) itemMap.get("product_description")
					: null;
			prdTitle = (prodDescMap != null)
					? ((prodDescMap.get("title") != null) ? prodDescMap.get("title").toString() : "")
					: "";
			logger.debug("Product Title found for id " + id + "is " + prdTitle);
			return prdTitle;
		} catch (HttpClientErrorException he) {
			logger.error("HttpClientErrorException caught >> " + he.getMessage());
			he.printStackTrace();
		} catch (RestClientException re) {
			logger.error("RestClientException caught >> " + re.getMessage());
			re.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception caught >> " + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Price getProductPrice(String id) {
		try {
			ProductPrice prdPrice = prdDetailDao.getProductPrice(id);
			if (prdPrice != null) {
				return new Price(prdPrice.getPrice(), prdPrice.getCurrencyCode());
			}
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException caught >> " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception caught >> " + e.getMessage());
			e.getStackTrace();
		}
		return null;
	}

	@Override
	public int updateProductPrice(String id, Price price) {
		try {
			int success = prdDetailDao.updateProductPrice(id, price);
			if (success == 1) {
				logger.debug("Succesfully updated the document with id " + id);
			}
			return success;
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException caught >> " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception caught >> " + e.getMessage());
			e.getStackTrace();
		}
		return 0;
	}
}
