package com.nguyen.microservices.core.product.businesslayer;

import com.mongodb.DuplicateKeyException;
import com.nguyen.api.core.product.Product;
import com.nguyen.microservices.core.product.datalayer.ProductEntity;
import com.nguyen.microservices.core.product.datalayer.ProductRepository;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.exceptions.NotFoundException;
import com.nguyen.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository repo;

    private final ProductMapper mapper;

    private final ServiceUtil serviceUtil;

    public ProductServiceImpl(ProductRepository repo, ProductMapper mapper, ServiceUtil serviceUtil) {
        this.repo = repo;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product getProductById(int productId) {
        ProductEntity entity = repo.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No product found for productId: "+productId));
        Product response = mapper.entityToModel(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());
        LOG.debug("Product getProductById: found productId: {}",response.getProductId());
        return response;
    }

    @Override
    public Product createProduct(Product model) {
        try{
            ProductEntity entity = mapper.modelToEntity(model);
            ProductEntity newEntity = repo.save(entity);
            LOG.debug("createProduct: entity created for productId: {}",model.getProductId());
            return mapper.entityToModel(newEntity);
        }catch(DuplicateKeyException e){
            throw new InvalidInputException("Duplicate key, productId: "+ model.getProductId());
        }
    }

    @Override
    public void deleteProduct(int productId) {
        LOG.debug("deleteProduct: entity deleted for productId: {}",productId);
        repo.findByProductId(productId).ifPresent(e->repo.delete(e));
    }
}
