package com.nguyen.microservices.core.product.businesslayer;

import com.nguyen.api.core.product.Product;

public interface ProductService {

    Product getProductById(int productId);

    Product createProduct(Product model);

    void deleteProduct(int productId);
}
