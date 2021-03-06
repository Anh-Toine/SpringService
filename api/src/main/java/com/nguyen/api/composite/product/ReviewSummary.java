package com.nguyen.api.composite.product;

public class ReviewSummary {
    private int reviewId;
    private String author;
    private String subject;
    private String content;

    public ReviewSummary(int reviewId, String author,  String subject, String content) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }

    public ReviewSummary() {
         this.reviewId = 0;
         this.author = "";
         this.subject = "";
         this.content = "";
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
