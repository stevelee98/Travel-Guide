package com.example.hoangviet.mytravelapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Places {

    public List<HashMap<String, String>> parse(JSONObject jsonObject) {
        JSONArray jsonArray = null;
        try {
            if(!jsonObject.isNull("results")){
                jsonArray = jsonObject.getJSONArray("results");

            }
            if(!jsonObject.isNull("candidates")){
                jsonArray = jsonObject.getJSONArray("candidates");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        //String icon = "";
        String placeName = "-NA-";
        String placeID = "";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        String photorReference = "";
        String rating = "0";
        String userTotalRating = "";
        String openNow = "-NA-";
        String phone ="";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating");
            }
            if (!googlePlaceJson.isNull("user_ratings_total")) {
                userTotalRating = googlePlaceJson.getString("user_ratings_total");
            }
            if (!googlePlaceJson.isNull("opening_hours")) {
                openNow = googlePlaceJson.getJSONObject("opening_hours").getString("open_now");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if (!googlePlaceJson.isNull("formatted_address")) {
                vicinity = googlePlaceJson.getString("formatted_address");
            }
            if (!googlePlaceJson.isNull("place_id")) {
                placeID = googlePlaceJson.getString("place_id");
            }
            if (!googlePlaceJson.isNull("photos")) {
                photorReference = googlePlaceJson.getJSONArray("photos").getJSONObject(0).getString("photo_reference");


            }

            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");


            //googlePlaceMap.put("icon",icon);
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("place_id", placeID);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
            googlePlaceMap.put("rating", rating);
            googlePlaceMap.put("open_now", openNow);
            googlePlaceMap.put("photo_reference", photorReference);
            googlePlaceMap.put("user_ratings_total", userTotalRating);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}
