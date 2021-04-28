package com.nguyen.microservices.core.recommendation.datalayer;

import com.nguyen.api.core.recommendation.Recommendation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity,String> {
    List<RecommendationEntity> findByProductId(int productId);
}
