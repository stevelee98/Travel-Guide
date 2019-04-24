package com.example.hoangviet.mytravelapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {

    String googlePlacesData = null;
    GoogleMap googleMap;
    RecyclerView recyclerView;
    CustomItemAdapter customItemAdapter;
    List<ItemList> list;

    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            googleMap = (GoogleMap) inputObj[0];
            String googlePlacesUrl = (String) inputObj[1];
            recyclerView = (RecyclerView) inputObj[2];
            customItemAdapter = (CustomItemAdapter) inputObj[3];
            list =(List<ItemList>) inputObj[4];

            Http http = new Http();

            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
        Object[] toPass = new Object[5];
        toPass[0] = googleMap;
        toPass[1] = result;
        toPass[2] = recyclerView;
        toPass[3] = customItemAdapter;
        toPass[4] = list;
        placesDisplayTask.execute(toPass);
    }
}
