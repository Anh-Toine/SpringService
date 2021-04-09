package com.nguyen.microservices.core.recommendation.presentationlayer.controllers;

import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.api.core.recommendation.RecommendationAPI;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationRESTController implements RecommendationAPI {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationRESTController.class);

    private final ServiceUtil serviceUtil;

    public RecommendationRESTController(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        List<Recommendation> recommendations = new ArrayList<>();
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId:" + productId);
        } else if (productId == 113) {
            LOG.debug("No recommendations found for productId: {}", productId);
            return recommendations;
        } else {
            recommendations.add(
                    new Recommendation(
                            productId,
                            1,
                            "Author 1",
                            1,
                            "Content 1",
                            serviceUtil.getServiceAddress()
                    ));
            recommendations.add(
                    new Recommendation(
                            productId,
                            2,
                            "Author 2",
                            2,
                            "Content 2",
                            serviceUtil.getServiceAddress()
                    ));
            recommendations.add(
                    new Recommendation(
                            productId,
                            3,
                            "Author 3",
                            3,
                            "Content 3",
                            serviceUtil.getServiceAddress()
                    ));
            recommendations.add(
                    new Recommendation(
                            productId,
                            4,
                            "Author 4",
                            4,
                            "Content 4",
                            serviceUtil.getServiceAddress()
                    ));
            recommendations.add(
                    new Recommendation(
                            productId,
                            5,
                            "Author 5",
                            5,
                            "Content 5",
                            serviceUtil.getServiceAddress()
                    ));
            LOG.debug("/recommendations found response size: {}", recommendations.size());
            return recommendations;

        }
    }
}