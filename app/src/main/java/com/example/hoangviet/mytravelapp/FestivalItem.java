package com.example.hoangviet.mytravelapp;

public class FestivalItem {
    private int ImgView;
    private String Name;
    public FestivalItem(){

    }
    public FestivalItem(String name, int imgView){
        this.Name = name;
        this.ImgView = imgView;
    }
    public String getName(){
        return  this.Name;
    }
    public  int getImgView(){
        return  this.ImgView;
    }

    public void setImgView(int imgView) {
        ImgView = imgView;
    }

    public void setName(String name) {
        Name = name;
    }
}
