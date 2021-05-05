package com.nguyen.microservices.core.review.presentationlayer.controllers;


import com.nguyen.api.core.review.Review;
import com.nguyen.api.core.review.ReviewServiceAPI;
import com.nguyen.microservices.core.review.businesslayer.ReviewService;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewRESTController implements ReviewServiceAPI {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewRESTController.class);

    private final ReviewService service;
    private final ServiceUtil serviceUtil;

    public ReviewRESTController(ReviewService service, ServiceUtil serviceUtil) {
        this.service = service;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReviews(int productId) {
        List<Review> reviews = service.findByProductId(productId);
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId:" + productId);
        }
            LOG.debug("/review found response size: {}", reviews.size());
            return reviews;
    }

    @Override
    public Review createReview(Review review) {
        Review rev = service.createReview(review);
        LOG.debug("Review createReview: created new review with ID {} for productId: {}",review.getReviewId(),review.getProductId());
        return rev;
    }

    @Override
    public void deleteReviews(int productId) {
        LOG.debug("Review deleteReviews: deleting all reviews for productId: {}",productId);
        service.deleteReviews(productId);
    }
}