/**
 * 
 */
package com.myretail.dao;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretail.domain.ProductDetailDao;
import com.myretail.domain.ProductPrice;
import com.myretail.model.Price;
import com.myretail.service.ProductDetailServiceImpl;
import com.myretail.util.Properties;

/**
 * @author ajishrajan Chicago, IL (Dec 2017)
 *
 */
@Service
public class ProductDetailDaoImpl implements ProductDetailDao {

	private static final Logger logger = LoggerFactory.getLogger(ProductDetailServiceImpl.class);

	@Autowired
	private Properties properties;

	@Override
	public ProductPrice getProductPrice(String id) throws MalformedURLException {
		try {
			String couchDbUrl = properties.getCouchDbUrl();
			logger.debug("couchDb Url is : " + couchDbUrl);
			String couchDbName = properties.getDatabaseName();
			logger.debug("couchDb Name is : " + couchDbName);

			HttpClient httpClient = new StdHttpClient.Builder().url(couchDbUrl).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
			CouchDbConnector couchDbCnctr = dbInstance.createConnector(couchDbName, true);
			ProductPrice prd = couchDbCnctr.get(ProductPrice.class, id);
			return prd;
		} catch (DocumentNotFoundException e) {
			logger.error("DocumentNotFoundException caught >> " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception caught >> " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateProductPrice(String id, Price price) throws MalformedURLException {
		try {
			String couchDbUrl = properties.getCouchDbUrl();
			logger.debug("couchDb Url is : " + couchDbUrl);
			String couchDbName = properties.getDatabaseName();
			logger.debug("couchDb Name is : " + couchDbName);
			
			HttpClient httpClient = new StdHttpClient.Builder().url(couchDbUrl).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
			CouchDbConnector couchDbCnctr = dbInstance.createConnector(couchDbName, true);
			ProductPrice prd = couchDbCnctr.get(ProductPrice.class, id);
			boolean updateFlag = false;
			if (prd != null && prd.getId()!=null) {
				if (price != null && price.getValue() != null && !price.getValue().equals(prd.getPrice())) {
					logger.debug("Price change identified for document id "+id);
					logger.debug("Old price = "+prd.getPrice());
					logger.debug("New price = "+price.getValue());
					prd.setPrice(price.getValue());
					updateFlag = true;
				}

				if (price != null && price.getCurrencyCode() != null
						&& !price.getCurrencyCode().equals(prd.getCurrencyCode())) {
					logger.debug("Change in currency code identified for document id "+id);
					logger.debug("Old currency code = "+prd.getCurrencyCode());
					logger.debug("New currency code = "+price.getCurrencyCode());
					prd.setCurrencyCode(price.getCurrencyCode());
					updateFlag = true;
				}

				if (updateFlag) {
					couchDbCnctr.update(prd);
					logger.debug("successfully updated the document");
					return 1;
				}else {
					logger.debug("No updates made");
					return 0;
				}
			} else {
				logger.debug("No document found for the id "+id);
				return 0;
			}
		} catch (DocumentNotFoundException e) {
			logger.error("DocumentNotFoundException caught >> " + e.getMessage());
			e.printStackTrace();
		} catch (UpdateConflictException e) {
			logger.error("UpdateConflictException caught >> " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception caught >> " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
}
