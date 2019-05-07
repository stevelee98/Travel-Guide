package com.example.hoangviet.mytravelapp.ItemsList;

public class HomeItemList {

    private String itemName;
    private String address;
    private String itemPlaceID;
    private String itemNumStar;
    private String userTotalRating;
    private String openNow;
    private String photoReference;

    private String ImgView;

    public HomeItemList(){

    }

    public HomeItemList(String itemName,String itemPlaceID, String address, String itemNumStar, String userTotalRating, String openNow, String photoReference, String imgView) {
        this.itemName = itemName;
        this.address = address;
        this.itemPlaceID = itemPlaceID;
        this.itemNumStar = itemNumStar;
        this.userTotalRating = userTotalRating;
        this.openNow = openNow;
        this.photoReference = photoReference;
        ImgView = imgView;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemPlaceID() {
        return itemPlaceID;
    }

    public void setItemPlaceID(String itemPlaceID) {
        this.itemPlaceID = itemPlaceID;
    }

    public String getItemNumStar() {
        return itemNumStar;
    }

    public void setItemNumStar(String itemNumStar) {
        this.itemNumStar = itemNumStar;
    }

    public String getUserTotalRating() {
        return userTotalRating;
    }

    public void setUserTotalRating(String userTotalRating) {
        this.userTotalRating = userTotalRating;
    }

    public String getOpenNow() {
        return openNow;
    }

    public void setOpenNow(String openNow) {
        this.openNow = openNow;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getImgView() {
        return ImgView;
    }

    public void setImgView(String imgView) {
        ImgView = imgView;
    }
}
