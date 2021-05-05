package com.nguyen.microservices.composite.product.businesslayer;

import com.nguyen.api.composite.product.ProductAggregate;

public interface ProductCompositeService {
    ProductAggregate getCompositeProduct(int productId);
    void createCompositeProduct(ProductAggregate model);
    void deleteCompositeProduct(int productId);
}
