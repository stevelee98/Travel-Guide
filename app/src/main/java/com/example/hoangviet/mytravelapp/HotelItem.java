package com.example.hoangviet.mytravelapp;

public class HotelItem {
    int imgHotel;
    String hotelName;
    String countHotel;
    String distanceHotel;
    float starHotel;

    public HotelItem(int imgHotel, String hotelName, String countHotel, String distanceHotel, float starHotel) {
        this.imgHotel = imgHotel;
        this.hotelName = hotelName;
        this.countHotel = countHotel;
        this.distanceHotel = distanceHotel;
        this.starHotel = starHotel;
    }

    public int getImgHotel(){
        return imgHotel;
    }

    public void setImgHotel(int imgHotel) {
        this.imgHotel = imgHotel;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCountHotel() {
        return countHotel;
    }

    public void setCountHotel(String countHotel) {
        this.countHotel = countHotel;
    }

    public String getDistanceHotel() {
        return distanceHotel;
    }

    public void setDistanceHotel(String distanceHotel) {
        this.distanceHotel = distanceHotel;
    }

    public float getStarHotel() {
        return starHotel;
    }

    public void setStarHotel(float starHotel) {
        this.starHotel = starHotel;
    }
}
