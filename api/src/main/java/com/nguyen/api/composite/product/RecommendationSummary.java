package com.nguyen.api.composite.product;



public class RecommendationSummary {
    private int recommendationId;
    private String author;
    private int rate;
    private String content;

    public RecommendationSummary(int recommentdationId, String author, int rate,String content) {
        this.recommendationId = recommentdationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }

    public RecommendationSummary() {
        this.recommendationId = 0;
        this.author = "";
        this.rate =  0;
        this.content = "";


    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommentdationId(int recommendationId) {
        this.recommendationId = recommendationId;
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
