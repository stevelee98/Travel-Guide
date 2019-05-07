package com.example.hoangviet.mytravelapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacePhoto {

    public List<String> parse(JSONObject jsonObject) {
        JSONObject object = null;

        try {
            object = jsonObject.getJSONObject("result");
            if(object==null){
                return  null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(object);
    }

    private List<String> getPlaces(JSONObject object) {

        List<String> googlePlacePhoto = new ArrayList<>();

        String photoReference = "";


        try {

            if (!object.isNull("photos")) {

                JSONArray jsonArray = object.getJSONArray("photos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    photoReference = jsonArray.getJSONObject(i).getString("photo_reference");
                    googlePlacePhoto.add(photoReference);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacePhoto;
    }
}
