package com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase;

import java.util.List;

public class FestivalItem {
    private String Name;
    private String Description;
    private List<String>  imgList;
    private String Day;

    public FestivalItem(String name, String description, List<String>   imgList, String day) {
        Name = name;
        Description = description;
        this.imgList = imgList;
        Day = day;
    }

    public FestivalItem(){

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<String>  getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }
}
