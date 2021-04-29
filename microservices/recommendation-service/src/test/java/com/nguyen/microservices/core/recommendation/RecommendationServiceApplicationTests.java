package com.nguyen.microservices.core.recommendation;

import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.microservices.core.recommendation.datalayer.RecommendationEntity;
import com.nguyen.microservices.core.recommendation.datalayer.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {"spring.data.mongodb.port: 0"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class RecommendationServiceApplicationTests {
	private static final int PRODUCT_ID_OKAY = 1;
	private static final int PRODUCT_ID_NOT_FOUND = 113;
	private static final int PRODUCT_ID_NEGATIVE_VALUE = -1;
	private static final String PRODUCT_ID_INVALID = "not-valid";

	private static final int RECOMMENDATION_ID = 1;

	@Autowired
	private RecommendationRepository repository;
	@Autowired
	private WebTestClient client;

	@BeforeEach
	public void setupDB(){
		repository.deleteAll();
	}

	@Test
	public void getRecommendationsByProductId(){
		int expectedLength = 3;

		//Add recommendations
		RecommendationEntity entity1 = new RecommendationEntity(PRODUCT_ID_OKAY,RECOMMENDATION_ID,"Author 1",1,"Content 1");
		repository.save(entity1);

		RecommendationEntity entity2 = new RecommendationEntity(PRODUCT_ID_OKAY,RECOMMENDATION_ID+1,"Author 2",1,"Content 2");
		repository.save(entity2);

		RecommendationEntity entity3 = new RecommendationEntity(PRODUCT_ID_OKAY,RECOMMENDATION_ID+2,"Author 3",1,"Content 3");
		repository.save(entity3);

		assertEquals(expectedLength,repository.findByProductId(PRODUCT_ID_OKAY).size());
		client.get()
				.uri("/recommendation?productId="+PRODUCT_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].productId").isEqualTo(PRODUCT_ID_OKAY)
				.jsonPath("$[1].productId").isEqualTo(PRODUCT_ID_OKAY)
				.jsonPath("$[2].productId").isEqualTo(PRODUCT_ID_OKAY);
	}

	@Test
	public void getRecommendationsMissingParameterString(){
		client.get()
				.uri("/recommendation")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Required int parameter 'productId' is not present");
	}
	@Test
	public void getRecommendationsInvalidParameterString(){
		client.get()
				.uri("/recommendation?productId="+PRODUCT_ID_INVALID)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}
	@Test
	public void getRecommendationsNegativeProductId(){
		client.get()
				.uri("/recommendation?productId="+PRODUCT_ID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/recommendation")
				.jsonPath("$.message").isEqualTo("Invalid productId:"+PRODUCT_ID_NEGATIVE_VALUE);
	}

	@Test
	public void getRecommendationsNotFound(){
		int expectedLength = 0;
		client.get()
				.uri("/recommendation?productId="+PRODUCT_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

	@Test
	public void createRecommendation(){
		int expectedSize = 1;
		//create recommendation
		Recommendation recommendation = new Recommendation(PRODUCT_ID_OKAY,RECOMMENDATION_ID,"Author " + RECOMMENDATION_ID,RECOMMENDATION_ID,"Content " + RECOMMENDATION_ID,"SA");
		//send POST request
		client.post()
				.uri("/recommendation")
				.body(just(recommendation), Recommendation.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize,repository.findByProductId(PRODUCT_ID_OKAY).size());
	}

	@Test
	public void deleteRecommendations(){
		int preExpectedSize = 1;
		int postExpectedSize = 0;
		RecommendationEntity entity = new RecommendationEntity(PRODUCT_ID_OKAY,RECOMMENDATION_ID,"Author 1",1,"Content 1");
		repository.save(entity);

		assertEquals(preExpectedSize,repository.findByProductId(PRODUCT_ID_OKAY).size());
		client.delete()
				.uri("/recommendation?productId="+PRODUCT_ID_OKAY)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertEquals(postExpectedSize,repository.findByProductId(PRODUCT_ID_OKAY).size());
	}

	@Test
	void contextLoads() {
	}

}
