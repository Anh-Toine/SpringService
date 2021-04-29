package com.nguyen.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class RecommendationSummary {
    private final int recommentdationId;
    private final String author;
    private final int rate;

    public RecommendationSummary(int recommentdationId, String author, int rate) {
        this.recommentdationId = recommentdationId;
        this.author = author;
        this.rate = rate;
    }

    public int getRecommentdationId() {
        return recommentdationId;
    }

    public String getAuthor() {
        return author;
    }

    public int getRate() {
        return rate;
    }
}
