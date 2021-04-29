package com.nguyen.microservices.composite.product.integrationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyen.api.core.product.Product;
import com.nguyen.api.core.product.ProductServiceAPI;
import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.api.core.recommendation.RecommendationAPI;
import com.nguyen.api.core.review.Review;
import com.nguyen.api.core.review.ReviewServiceAPI;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.exceptions.NotFoundException;
import com.nguyen.utils.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCompositeIntegration implements ProductServiceAPI, RecommendationAPI, ReviewServiceAPI {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,

            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort,

            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") String recommendationServicePort,

            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") String reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" +
                recommendationServicePort + "/recommendation?productId=";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";

    }

    @Override
    public Product getProduct(int productId) {
        try{
            String url = productServiceUrl + productId;
            LOG.debug("Will call getProduct API on URL:" + url);

            Product product = restTemplate.getForObject(url, Product.class);
            LOG.debug("Found a product with Id: {}",product.getProductId());

            return product;
        }catch (HttpClientErrorException ex){ //since we are the API client, we need to handle HTTP errors
            switch(ex.getStatusCode()){
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(ex));
                default:
                    LOG.debug("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        try{
            String url = recommendationServiceUrl + productId;
            LOG.debug("Will call getRecommendation API on URL: {}", url);

            List<Recommendation> recommendations = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Recommendation>>() {
                    }).getBody();

            LOG.debug("Found {} recommendations for a product with Id: {}", recommendations.size(), productId);
            return recommendations;
        }catch (Exception ex){
            LOG.debug("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Recommendation createRecommendation(Recommendation model) {
        return null;
    }

    @Override
    public void deleteRecommendations(int productId) {

    }

    @Override
    public List<Review> getReviews(int productId) {
        try{
            String url = reviewServiceUrl + productId;
            LOG.debug("Will call getReviews API on URL: {}", url);

            List<Review> reviews = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    }).getBody();

            LOG.debug("Found {} reviews for a product with Id: {}", reviews.size(), productId);
            return reviews;
        }catch (Exception ex){
            LOG.debug("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try{
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex){
            return ioex.getMessage();
        }
    }
}
