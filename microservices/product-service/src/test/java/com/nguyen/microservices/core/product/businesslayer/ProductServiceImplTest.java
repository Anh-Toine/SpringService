package com.nguyen.microservices.core.product.businesslayer;

import com.nguyen.api.core.product.Product;
import com.nguyen.microservices.core.product.datalayer.ProductEntity;
import com.nguyen.microservices.core.product.datalayer.ProductRepository;
import com.nguyen.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @DisplayName("getByProductId Product Found")
    @Test
    public void test_getProductByIdValid(){
        // arrange
        ProductEntity entity = new ProductEntity(1,"n",1);
        when(productRepository.findByProductId(1)).thenReturn(Optional.of(entity));
        // act
        Product returnProduct = productService.getProductById(1);
        // assert
        assertThat(returnProduct.getProductId()).isEqualTo(1);
    }

    @DisplayName("getByProductId Product Not Found")
    @Test
    public void test_getProductByIdNotFoundException(){
        assertThrows(NotFoundException.class, () ->{
            productService.getProductById(1);
        });
    }

    /*
        Create some test cases for these 2 methods:
        1. deleteProduct
        2. createProduct
     */
}