package com.nguyen.microservices.core.recommendation.businesslayer;

import com.nguyen.api.core.recommendation.Recommendation;

import java.util.List;

public interface RecommendationService {
    List<Recommendation> findByProductId(int productId);
    Recommendation createRecommendation(Recommendation model);
    void deleteRecommendation(int productId);
}
