package com.nguyen.microservices.core.product.datalayer;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductEntity {
    @Id
    private String id;
    @Version
    private Integer version;

    @Indexed(unique = true)
    private int productId;
    private String name;
    private int weight;

    public ProductEntity(int productId, String name, int weight) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
    }

    public ProductEntity() {
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
