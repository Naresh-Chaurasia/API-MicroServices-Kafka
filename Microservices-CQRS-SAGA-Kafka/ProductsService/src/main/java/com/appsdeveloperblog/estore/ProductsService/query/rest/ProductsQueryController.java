package com.appsdeveloperblog.estore.ProductsService.query.rest;

//import com.appsdeveloperblog.estore.ProductsService.query.FindProductsQuery;
import com.appsdeveloperblog.estore.ProductsService.query.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductsQueryController {
	
	@Autowired
	QueryGateway queryGateway;
	
	@GetMapping
	public List<ProductRestModel> getProducts() {
		
		FindProductsQuery findProductsQuery = new FindProductsQuery();
		System.out.println("-------------------------ProductsQueryController/getProducts/@GetMapping calls queryGateway.query(..)-------------------------");
		List<ProductRestModel> products = queryGateway.query(findProductsQuery,
				ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		
		return products;
		
		
	}

}
