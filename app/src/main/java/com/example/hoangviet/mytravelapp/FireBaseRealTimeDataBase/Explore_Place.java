package com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase;

public class Explore_Place{
    public String name;
    public String description;

    public Explore_Place() {

    }

    public Explore_Place(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
