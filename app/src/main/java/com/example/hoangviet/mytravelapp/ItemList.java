package com.example.hoangviet.mytravelapp;

import java.util.HashMap;

public class ItemList extends HashMap<String, String> {
    private String itemName;
    private String itemNumStar;
    private String itemDistance;
    private String openNow;
    private String address;
    private String photoReference;
    private String userTotalRating;


    public ItemList() {
    }

    public ItemList(String itemName,String address, String itemNumStar,String userTotalRating, String itemDistance, String openNow, String photoReference) {
        this.itemName = itemName;
        this.itemNumStar = itemNumStar;
        this.itemDistance = itemDistance;
        this.openNow = openNow;
        this.address = address;
        this.photoReference = photoReference;
        this.userTotalRating = userTotalRating;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumStar() {
        return itemNumStar;
    }

    public void setItemNumStar(String itemNumStar) {
        this.itemNumStar = itemNumStar;
    }

    public String getItemDistance() {
        return itemDistance;
    }

    public void setItemDistance(String itemDistance) {
        this.itemDistance = itemDistance;
    }

    public String getopenNow() {
        return openNow;
    }

    public void setopenNow(String openNow) {
        this.openNow = openNow;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getUserTotalRating() {
        return userTotalRating;
    }

    public void setUserTotalRating(String userTotalRating) {
        this.userTotalRating = userTotalRating;
    }
}
