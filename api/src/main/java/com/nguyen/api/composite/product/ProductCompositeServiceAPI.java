package com.nguyen.api.composite.product;

import org.springframework.web.bind.annotation.*;

public interface ProductCompositeServiceAPI {

    @GetMapping(value = "/product-composite/{productId}", produces = "application/json")
    ProductAggregate getProduct(@PathVariable int productId);

    @PostMapping(value = "/product-composite", produces = "application/json")
    void createCompositeProduct(@RequestBody ProductAggregate productAggregate);

    @DeleteMapping(value = "/product-composite/{productId}")
    void deleteCompositeProduct(@PathVariable int productId);
}
