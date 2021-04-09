package com.nguyen.microservices.core.review.presentationlayer.controllers;


import com.nguyen.api.core.review.Review;
import com.nguyen.api.core.review.ReviewServiceAPI;
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

    private final ServiceUtil serviceUtil;

    public ReviewRESTController(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReviews(int productId) {
        List<Review> reviews = new ArrayList<>();
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId:" + productId);
        } else if (productId == 213) {
            LOG.debug("No reviews found for productId: {}", productId);
            return reviews;
        } else {
            reviews.add(new Review(
                    productId,
                    1,
                    "Author 1",
                    "Subject 1",
                    "Content 1",
                    serviceUtil.getServiceAddress()
            ));
            reviews.add(new Review(
                    productId,
                    2,
                    "Author 2",
                    "Subject 2",
                    "Content 2",
                    serviceUtil.getServiceAddress()
            ));
            reviews.add(new Review(
                    productId,
                    3,
                    "Author 3",
                    "Subject 3",
                    "Content 3",
                    serviceUtil.getServiceAddress()
            ));
            reviews.add(new Review(
                    productId,
                    4,
                    "Author 4",
                    "Subject 4",
                    "Content 4",
                    serviceUtil.getServiceAddress()
            ));
            reviews.add(new Review(
                    productId,
                    5,
                    "Author 5",
                    "Subject 5",
                    "Content 5",
                    serviceUtil.getServiceAddress()
            ));
            LOG.debug("/review found response size: {}", reviews.size());
            return reviews;

        }
    }
}