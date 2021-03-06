package com.nguyen.microservices.core.recommendation.presentationlayer.controllers;

import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.api.core.recommendation.RecommendationAPI;
import com.nguyen.microservices.core.recommendation.businesslayer.RecommendationService;
import com.nguyen.utils.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationRESTController implements RecommendationAPI {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationRESTController.class);
    private final RecommendationService recommendationService;


    public RecommendationRESTController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId:" + productId);
        }else {
            List<Recommendation> recommendations = recommendationService.findByProductId(productId);
            LOG.debug("/recommendations found response size: {}", recommendations.size());
            return recommendations;
        }
    }

    @Override
    public Recommendation createRecommendation(Recommendation model) {
        Recommendation recommendation = recommendationService.createRecommendation(model);
        LOG.debug("RecommendationRESTController createRecommendation: created an entity: {} / {}",recommendation.getProductId(),recommendation.getRecommendationId());
        return recommendation;
    }

    @Override
    public void deleteRecommendations(int productId) {
        LOG.debug("RecommendationRESTController deleteRecommendations: trying to delete all recommendations for product with ID: {}",productId);
        recommendationService.deleteRecommendation(productId);
    }
}