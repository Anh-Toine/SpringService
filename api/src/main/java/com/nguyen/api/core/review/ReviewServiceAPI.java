package com.nguyen.api.core.review;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewServiceAPI {
    @GetMapping(value="/review",produces = "application/json")
    List<Review> getReviews(@RequestParam(value = "productId", required = true) int productId);

    @PostMapping(value = "/review", produces = "application/json")
    Review createReview(@RequestBody Review review);

    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(value = "productId", required = true) int productId);
}
