package com.example.hoangviet.mytravelapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hoangviet.mytravelapp.Adapter.HomeItemAdapter;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.example.hoangviet.mytravelapp.google.PlacePhotoDisplayTask;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {

    private static final int NEARBY_SEARCH = 0;
    private static final int PLACE_PHOTO = 1;



    int typeSearch;
    String googlePlacesData = null;
    GoogleMap googleMap;
    RecyclerView recyclerView;

    //dành cho search nearby
    CustomItemAdapter customItemAdapter;
    List<ItemList> list;

    //dành cho place photos
    PlacePhotoAdapter placePhotoAdapter;
    List<PlacePhotoItem> placePhotoList;

    HomeItemAdapter homeItemAdapter;
    List<HomeItemList> homeItemLists;

    @Override
    protected String doInBackground(Object... inputObj) {
        try {

            if( (int) inputObj[0] == NEARBY_SEARCH){

                if(inputObj[1] == null) {
                    typeSearch = (int) inputObj[0];
                    googleMap = (GoogleMap) inputObj[1];
                    String googlePlacesUrl = (String) inputObj[2];
                    recyclerView = (RecyclerView) inputObj[3];
                    homeItemAdapter = (HomeItemAdapter) inputObj[4];
                    homeItemLists =(List<HomeItemList>) inputObj[5];
                    Http http = new Http();

                    googlePlacesData = http.read(googlePlacesUrl);

                }
                else{
                    typeSearch = (int) inputObj[0];
                    googleMap = (GoogleMap) inputObj[1];
                    String googlePlacesUrl = (String) inputObj[2];
                    recyclerView = (RecyclerView) inputObj[3];
                    customItemAdapter = (CustomItemAdapter) inputObj[4];
                    list =(List<ItemList>) inputObj[5];
                    Http http = new Http();

                    googlePlacesData = http.read(googlePlacesUrl);
                }

            }
            if( (int) inputObj[0] == PLACE_PHOTO){
                typeSearch = (int) inputObj[0];
                String placePhotoUrl = (String) inputObj[1];
                recyclerView = (RecyclerView) inputObj[2];
                placePhotoAdapter = (PlacePhotoAdapter) inputObj[3];
                placePhotoList =(List<PlacePhotoItem>) inputObj[4];

                Http http = new Http();

                googlePlacesData = http.read(placePhotoUrl);

            }

        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
        PlacePhotoDisplayTask placePhotoDisplayTask = new PlacePhotoDisplayTask();

        if(typeSearch == NEARBY_SEARCH) {
            Object[] toPass = new Object[6];

            if(googleMap == null){
                toPass[0] = typeSearch;
                toPass[1] = googleMap;
                toPass[2] = result;
                toPass[3] = recyclerView;
                toPass[4] = homeItemAdapter;
                toPass[5] = homeItemLists;
                placesDisplayTask.execute(toPass);
            }
            else{
                toPass[0] = typeSearch;
                toPass[1] = googleMap;
                toPass[2] = result;
                toPass[3] = recyclerView;
                toPass[4] = customItemAdapter;
                toPass[5] = list;
                placesDisplayTask.execute(toPass);
            }
        }
        if(typeSearch == PLACE_PHOTO){
            Object[] toPass = new Object[5];
            toPass[0] = typeSearch;
            toPass[1] = result;
            toPass[2] = recyclerView;
            toPass[3] = placePhotoAdapter;
            toPass[4] = placePhotoList;
            placePhotoDisplayTask.execute(toPass);
        }

    }
}
