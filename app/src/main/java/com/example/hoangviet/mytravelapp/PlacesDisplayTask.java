package com.example.hoangviet.mytravelapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by DinhTien on 26-12-2015.
 */
public class PlacesDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

    JSONObject googlePlacesJson;
    GoogleMap googleMap;

    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

        List<HashMap<String, String>> googlePlacesList = null;
        Places placeJsonParser = new Places();

        try {
            googleMap = (GoogleMap) inputObj[0];
            googlePlacesJson = new JSONObject((String) inputObj[1]);
            googlePlacesList = placeJsonParser.parse(googlePlacesJson);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {

        googleMap.clear();
        for (int i = 0; i < list.size(); i++) {


//            MarkerOptions markerOptions = new MarkerOptions();
//            HashMap<String, String> googlePlace = list.get(i);
//            double lat = Double.parseDouble(googlePlace.get("lat"));
//            double lng = Double.parseDouble(googlePlace.get("lng"));
//            String placeName = googlePlace.get("place_name");
//            String vicinity = googlePlace.get("vicinity");
//            //String iCon = googlePlace.get("icon");
//            LatLng latLng = new LatLng(lat, lng);
//            //markerOptions.icon(BitmapDescriptorFactory.fromPath("https://maps.gstatic.com/mapfiles/place_api/icons/gas_station-71.png"));
//            markerOptions.position(latLng);
//            markerOptions.title(placeName);
//            markerOptions.snippet(vicinity);
//            googleMap.addMarker(markerOptions);

        }
    }
}
