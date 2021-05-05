package com.nguyen.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class RecommendationSummary {
    private int recommentdationId;
    private String author;
    private int rate;
    private String content;

    public RecommendationSummary(int recommentdationId, String author, int rate,String content) {
        this.recommentdationId = recommentdationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }

    public RecommendationSummary() {
        this.recommentdationId = 0;
        this.author = "";
        this.rate =  0;
        this.content = "";


    }

    public int getRecommendationId() {
        return recommentdationId;
    }

    public void setRecommentdationId(int recommentdationId) {
        this.recommentdationId = recommentdationId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
