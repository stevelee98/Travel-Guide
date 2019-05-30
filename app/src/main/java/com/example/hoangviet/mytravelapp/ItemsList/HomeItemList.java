package com.example.hoangviet.mytravelapp.ItemsList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class HomeItemList extends HashMap<String, String> implements Parcelable {
    private double latitude;
    private double longtitude;
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

    public HomeItemList(String itemName,String itemPlaceID, String address, String itemNumStar, String userTotalRating, String openNow, String photoReference, String imgView,double lat, double lng) {
        this.itemName = itemName;
        this.address = address;
        this.itemPlaceID = itemPlaceID;
        this.itemNumStar = itemNumStar;
        this.userTotalRating = userTotalRating;
        this.openNow = openNow;
        this.photoReference = photoReference;
        ImgView = imgView;
        this.latitude = lat;
        this.longtitude = lng;
    }

    protected HomeItemList(Parcel in) {
        itemName = in.readString();
        address = in.readString();
        itemPlaceID = in.readString();
        itemNumStar = in.readString();
        userTotalRating = in.readString();
        openNow = in.readString();
        photoReference = in.readString();
        ImgView = in.readString();
    }

    public static final Creator<HomeItemList> CREATOR = new Creator<HomeItemList>() {
        @Override
        public HomeItemList createFromParcel(Parcel in) {
            return new HomeItemList(in);
        }

        @Override
        public HomeItemList[] newArray(int size) {
            return new HomeItemList[size];
        }
    };

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(address);
        dest.writeString(itemPlaceID);
        dest.writeString(itemNumStar);
        dest.writeString(userTotalRating);
        dest.writeString(openNow);
        dest.writeString(photoReference);


    }
//    public static final Parcelable.Creator<HomeItemList> CREATOR = new Parcelable.Creator<HomeItemList>() {
//        public HomeItemList createFromParcel(Parcel in) {
//            return new HomeItemList(in);
//        }
//
//        public HomeItemList[] newArray(int size) {
//            return new HomeItemList[size];
//
//        }
//    };
}
