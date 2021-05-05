package com.nguyen.microservices.core.review;

import com.nguyen.api.core.review.Review;
import com.nguyen.microservices.core.review.datalayer.ReviewEntity;
import com.nguyen.microservices.core.review.datalayer.ReviewRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:review-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class ReviewServiceApplicationTests {
	private static final int PRODUCT_ID_OKAY = 1;
	private static final int PRODUCT_ID_NOT_FOUND = 213;
	private static final int PRODUCT_ID_NEGATIVE_VALUE = -1;
	private static final String PRODUCT_ID_INVALID = "not-valid";

	@Autowired
	private ReviewRepository repo;
	@Autowired
	private WebTestClient client;

	@BeforeEach
	private void setupDb(){
		repo.deleteAll();
	}

	@Test
	public void getReviewsByProductId(){
		int expectedLength = 3;
		ReviewEntity reviewEntity = new ReviewEntity(PRODUCT_ID_OKAY,PRODUCT_ID_OKAY,"a1","s1","c1");
		repo.save(reviewEntity);
		ReviewEntity reviewEntity2 = new ReviewEntity(PRODUCT_ID_OKAY,PRODUCT_ID_OKAY+1,"a2","s2","c2");
		repo.save(reviewEntity2);
		ReviewEntity reviewEntity3 = new ReviewEntity(PRODUCT_ID_OKAY,PRODUCT_ID_OKAY+2,"a3","s3","c3");
		repo.save(reviewEntity3);
		assertEquals(expectedLength,repo.findByProductId(PRODUCT_ID_OKAY).size());

		client.get()
				.uri("/review?productId="+PRODUCT_ID_OKAY)
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
	public void createReview(){
		int expectedLength = 1;
		Review review = new Review(PRODUCT_ID_OKAY,PRODUCT_ID_OKAY,"a1","s1","c1","SA");
		client.post()
				.uri("/review")
				.body(just(review),Review.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedLength,repo.findByProductId(PRODUCT_ID_OKAY).size());
	}

	@Test
	public void deleteReviewsByProductId(){
		int preExpectedLength = 1;
		int postExpectedLength = 0;
		ReviewEntity reviewEntity = new ReviewEntity(PRODUCT_ID_OKAY,PRODUCT_ID_OKAY,"a1","s1","c1");
		repo.save(reviewEntity);
		assertEquals(preExpectedLength,repo.findByProductId(PRODUCT_ID_OKAY).size());
		client.delete()
				.uri("/review?productId="+PRODUCT_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();
		assertEquals(postExpectedLength,repo.findByProductId(PRODUCT_ID_OKAY).size());
	}
	@Test
	public void getReviewsMissingParameterString(){
		client.get()
				.uri("/review")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Required int parameter 'productId' is not present");
	}
	@Test
	public void getReviewsInvalidParameterString(){
		client.get()
				.uri("/review?productId="+PRODUCT_ID_INVALID)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}
	@Test
	public void getReviewsNegativeProductId(){
		client.get()
				.uri("/review?productId="+PRODUCT_ID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Invalid productId:"+PRODUCT_ID_NEGATIVE_VALUE);
	}

	@Test
	public void getReviewsNotFound(){
		int expectedLength = 0;
		client.get()
				.uri("/review?productId="+PRODUCT_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}


	@Test
	void contextLoads() {
	}

}
