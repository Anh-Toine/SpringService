package com.nguyen.microservices.core.review.businesslayer;

import com.nguyen.api.core.review.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findByProductId(int productId);
    Review createReview(Review model);
    void deleteReviews(int productId);

}
