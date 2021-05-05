package com.nguyen.microservices.composite.product;

import com.nguyen.api.core.product.Product;
import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.api.core.review.Review;
import com.nguyen.microservices.composite.product.integrationlayer.ProductCompositeIntegration;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class ProductCompositeServiceApplicationTests {

	private static final int PRODUCT_ID_OKAY = 1;
	private static final int PRODUCT_ID_NOT_FOUND = 213;
	private static final int PRODUCT_ID_NEGATIVE_VALUE = -1;
	private static final String PRODUCT_ID_INVALID = "not-valid";

	@Autowired
	private WebTestClient client;

	@MockBean
	private ProductCompositeIntegration compositeIntegration;

	//Run this method b4 each test method
	@BeforeEach
	public void setup(){
		when(compositeIntegration.getProduct(PRODUCT_ID_OKAY))
				.thenReturn(new Product(PRODUCT_ID_OKAY,"name 1",1,"mock address"));
		//BDD equivalent
		given(compositeIntegration.getProduct(PRODUCT_ID_OKAY))
				.willReturn(new Product(PRODUCT_ID_OKAY,"name 1",1,"mock address"));

		when(compositeIntegration.getRecommendations(PRODUCT_ID_OKAY))
				.thenReturn(Collections.singletonList(new Recommendation(PRODUCT_ID_OKAY,1,"author 1",1,"content 1","mock addresss")));
		when(compositeIntegration.getReviews(PRODUCT_ID_OKAY))
				.thenReturn(Collections.singletonList(new Review(PRODUCT_ID_OKAY,1,"author 1","subject 1","content 1","mock addresss")));

		when(compositeIntegration.getProduct(PRODUCT_ID_NOT_FOUND))
				.thenThrow(new NotFoundException("NOT FOUND: "+PRODUCT_ID_NOT_FOUND));

		when(compositeIntegration.getProduct(PRODUCT_ID_NEGATIVE_VALUE))
				.thenThrow(new InvalidInputException("INVALID INPUT: "+PRODUCT_ID_NEGATIVE_VALUE));

	}
	@Test
	public void getProductById(){

		int expectedLength = 1;
		client.get()
				.uri("/product-composite/"+PRODUCT_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.productId").isEqualTo(PRODUCT_ID_OKAY)
				.jsonPath("$.recommendations.length()").isEqualTo(expectedLength)
				.jsonPath("$.reviews.length()").isEqualTo(expectedLength);

	}
	@Test
	public void getProductNotFound(){
			//int expectedLength = 1;
			client.get()
					.uri("/product-composite/"+PRODUCT_ID_NOT_FOUND)
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().isNotFound()
					.expectHeader().contentType(MediaType.APPLICATION_JSON)
					.expectBody()
					.jsonPath("$.path").isEqualTo("/product-composite/"+PRODUCT_ID_NOT_FOUND)
					.jsonPath("$.message").isEqualTo("NOT FOUND: "+PRODUCT_ID_NOT_FOUND);

	}
	@Test
	public void getProductNegativeValue(){
		client.get()
				.uri("/product-composite/"+PRODUCT_ID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product-composite/"+PRODUCT_ID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("INVALID INPUT: "+PRODUCT_ID_NEGATIVE_VALUE);
	}
	@Test
	public void getProductStringParameter(){
		client.get()
				.uri("/product-composite/"+PRODUCT_ID_INVALID)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product-composite/"+PRODUCT_ID_INVALID)
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}
	@Test
	void contextLoads() {
	}

}
