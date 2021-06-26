package com.bytedata.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bytedata.beans.ProductEnquiryBean;
import com.bytedata.client.ProductStockClient;

@RestController
public class ProductEnquiryController {
	@Autowired
	ProductStockClient productStockClient;

	@GetMapping("/product-enquiry/name/{name}/productAvailability/{productAvailability}/unit/{unit}")
	public ProductEnquiryBean getEnquiryOfProduct(@PathVariable String name, @PathVariable String productAvailability,
			@PathVariable int unit) {
		ProductEnquiryBean productEnquiryBean = productStockClient.checkProductStock(name, productAvailability);

		double totalPrice = productEnquiryBean.getProductPrice().doubleValue()* unit;
		double discounts = productEnquiryBean.getDiscountOffer();
		double discountPrice = totalPrice - totalPrice * discounts / 100;

		return new ProductEnquiryBean(productEnquiryBean.getId(), name, productEnquiryBean.getProductPrice(),
				productAvailability,productEnquiryBean.getDiscountOffer(), unit,discountPrice, productEnquiryBean.getPort()
				);
	}

}
//Long id, String productName, BigDecimal productPrice, String productAvailability,double discountOffer, int unit, double totalPrice, int port