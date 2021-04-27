package com.nguyen.microservices.core.product.businesslayer;

import com.nguyen.api.core.product.Product;
import com.nguyen.microservices.core.product.datalayer.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "serviceAddress", ignore = true)
    Product entityToModel(ProductEntity entity);
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    ProductEntity modelToEntity(Product model);
}
