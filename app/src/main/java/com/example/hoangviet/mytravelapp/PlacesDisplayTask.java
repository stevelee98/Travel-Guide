package com.example.hoangviet.mytravelapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hoangviet.mytravelapp.Adapter.HomeItemAdapter;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PlacesDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

    JSONObject googlePlacesJson;
    GoogleMap googleMap;
    RecyclerView recyclerView;

    int typeSearch;

    CustomItemAdapter customItemAdapter;
    List<ItemList> list;

    HomeItemAdapter homeItemAdapter;
    List<HomeItemList> homeItemLists;

    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

        List<HashMap<String, String>> googlePlacesList = null;
        Places placeJsonParser = new Places();
        try {

            if(inputObj[1] == null){
                typeSearch = (int) inputObj[0];
                googleMap = (GoogleMap) inputObj[1];
                googlePlacesJson = new JSONObject((String) inputObj[2]);
                recyclerView = (RecyclerView) inputObj[3];
                homeItemAdapter = (HomeItemAdapter) inputObj[4];
                homeItemLists = (List<HomeItemList>) inputObj[5];
            }
            else{
                typeSearch = (int) inputObj[0];
                googleMap = (GoogleMap) inputObj[1];
                googlePlacesJson = new JSONObject((String) inputObj[2]);
                recyclerView = (RecyclerView) inputObj[3];
                customItemAdapter = (CustomItemAdapter) inputObj[4];
                list = (List<ItemList>) inputObj[5];

            }
            googlePlacesList = placeJsonParser.parse(googlePlacesJson);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> mapDatalist) {

            if(googleMap == null){
                for (int i = 0; i < mapDatalist.size(); i++) {
                    HashMap<String, String> googlePlace = mapDatalist.get(i);

//                    double lat = Double.parseDouble((googlePlace.get("lat")));
//                    double lng = Double.parseDouble((googlePlace.get("lng")));
                    String placeName = googlePlace.get("place_name");
                    String vicinity = googlePlace.get("vicinity");
                    String rating = "0";
                    String open_now = "";
                    String photoReference = "";
                    String userTotalRating = "";
                    String placeID ="";


//                    if(googlePlace.get("open_now").equals("true")){
//                        open_now = "open";
//                    }
//                    else if(googlePlace.get("open_now").equals("false")) {
//                        open_now = "lose";
//                    }
//                    else{
//                        open_now ="";
//                    }

                    if(googlePlace.get("user_ratings_total") != null){
                        userTotalRating = googlePlace.get("user_ratings_total");
                    }
                    if(googlePlace.get("photo_reference") != null){
                        photoReference = googlePlace.get("photo_reference");
                    }
                    if(googlePlace.get("rating") != null){
                        rating = googlePlace.get("rating");
                    }

                    placeID = googlePlace.get("place_id");
                    StringBuilder photoURL =  new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxheight=300&photoreference=" );
                    photoURL.append(photoReference.toString());
                    photoURL.append("&key=AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8");

                    try{
                        HomeItemList item = new HomeItemList(placeName,placeID, vicinity,rating, userTotalRating,open_now, photoReference, photoURL.toString());
                        homeItemLists.add(item);
                    }
                    catch (NullPointerException e){
                        Log.e("Exception: ", e.getMessage());
                    }
                }
                homeItemAdapter.notifyDataSetChanged();
            }
            else{
                googleMap.clear();
                for (int i = 0; i < mapDatalist.size(); i++) {

                    MarkerOptions markerOptions = new MarkerOptions();

                    HashMap<String, String> googlePlace = mapDatalist.get(i);

                    double lat = Double.parseDouble(googlePlace.get("lat"));
                    double lng = Double.parseDouble(googlePlace.get("lng"));
                    String placeName = googlePlace.get("place_name");
                    String vicinity = googlePlace.get("vicinity");
                    String rating = "0";
                    String open_now = "";
                    String photoReference = "";
                    String userTotalRating = "";
                    String placeID ="";


                    if(googlePlace.get("open_now").equals("true")){
                        open_now = "open";
                    }
                    else if(googlePlace.get("open_now").equals("false")) {
                        open_now = "lose";
                    }
                    else{
                        open_now ="";
                    }

                    if(googlePlace.get("user_ratings_total") != null){
                        userTotalRating = googlePlace.get("user_ratings_total");
                    }
                    if(googlePlace.get("photo_reference") != null){
                        photoReference = googlePlace.get("photo_reference");
                    }

                    rating = googlePlace.get("rating");
                    placeID = googlePlace.get("place_id");

                    //String iCon = googlePlace.get("icon");
                    LatLng latLng = new LatLng(lat, lng);
                    //markerOptions.icon(BitmapDescriptorFactory.fromPath("https://maps.gstatic.com/mapfiles/place_api/icons/gas_station-71.png"));
                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : "+ vicinity);
                    //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                    googleMap.addMarker(markerOptions);
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                    try{
                        ItemList item = new ItemList(placeName,placeID, vicinity,rating, userTotalRating,"",open_now, photoReference);
                        list.add(item);
                    }
                    catch (NullPointerException e){
                        Log.e("Exception: ", e.getMessage());
                    }
                }
                customItemAdapter.notifyDataSetChanged();
            }


    }
}
