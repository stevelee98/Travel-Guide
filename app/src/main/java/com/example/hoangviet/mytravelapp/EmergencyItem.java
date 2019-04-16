package com.example.hoangviet.mytravelapp;

public class EmergencyItem {
    private String emName;
    private String emRating;
    private String emRatingCount;
    private String emDistance;

    public EmergencyItem() {
    }

    public EmergencyItem(String emName, String emRating, String emRatingCount, String emDistance) {
        this.emName = emName;
        this.emRating = emRating;
        this.emRatingCount = emRatingCount;
        this.emDistance = emDistance;
    }

    public String getEmName() {
        return this.emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getEmRating() {
        return emRating;
    }

    public void setEmRating(String emRating) {
        this.emRating = emRating;
    }

    public String getEmRatingCount() {
        return emRatingCount;
    }

    public void setEmRatingCount(String emRatingCount) {
        this.emRatingCount = emRatingCount;
    }

    public String getEmDistance() {
        return emDistance;
    }

    public void setEmDistance(String emDistance) {
        this.emDistance = emDistance;
    }
}
