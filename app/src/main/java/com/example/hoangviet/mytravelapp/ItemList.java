package com.example.hoangviet.mytravelapp;

public class ItemList {
    private String itemName;
    private String itemRating;
    private String itemRatingCount;
    private String itemDistance;

    public ItemList() {
    }

    public ItemList(String itemName, String itemRating, String itemRatingCount, String itemDistance) {
        this.itemName = itemName;
        this.itemRating = itemRating;
        this.itemRatingCount = itemRatingCount;
        this.itemDistance = itemDistance;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemRating() {
        return itemRating;
    }

    public void setItemRating(String itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemRatingCount() {
        return itemRatingCount;
    }

    public void setItemRatingCount(String itemRatingCount) {
        this.itemRatingCount = itemRatingCount;
    }

    public String getItemDistance() {
        return itemDistance;
    }

    public void setItemDistance(String itemDistance) {
        this.itemDistance = itemDistance;
    }
}
