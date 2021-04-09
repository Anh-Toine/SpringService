package com.nguyen.microservices.core.product.presentationlayer.controllers;

import com.nguyen.api.core.product.Product;
import com.nguyen.api.core.product.ProductServiceAPI;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.exceptions.NotFoundException;
import com.nguyen.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRESTController implements ProductServiceAPI {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRESTController.class);

    private final ServiceUtil serviceUtil;

    public ProductRESTController(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product getProduct(int productId) {

        LOG.debug("/product MS returns found product for productId: "+productId);

        if(productId < 1){
            throw new InvalidInputException("Invalid productId: "+productId);
        }
        if(productId ==  13){
            throw new NotFoundException("No product found for productId: "+productId);
        }
        Product p = new Product(productId,"name-",123,serviceUtil.getServiceAddress());
        return p;
    }
}
