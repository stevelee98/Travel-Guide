package com.example.hoangviet.mytravelapp.google;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hoangviet.mytravelapp.CustomItemAdapter;
import com.example.hoangviet.mytravelapp.ItemList;
import com.example.hoangviet.mytravelapp.PlacePhoto;
import com.example.hoangviet.mytravelapp.PlacePhotoAdapter;
import com.example.hoangviet.mytravelapp.PlacePhotoItem;
import com.example.hoangviet.mytravelapp.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class PlacePhotoDisplayTask extends AsyncTask<Object, Integer, List<String>> {


    JSONObject googlePlacesJson;

    RecyclerView recyclerView;

    int typeSearch;


    PlacePhotoAdapter placePhotoAdapter;
    List<PlacePhotoItem> placePhotoList;
    List<String>  googlePlacesList = null;

    @Override
    protected List<String> doInBackground(Object... inputObj) {

        PlacePhoto placePhotoJsonParser = new PlacePhoto();
        try {
                typeSearch = (int) inputObj[0];
                googlePlacesJson = new JSONObject((String) inputObj[1]);
                recyclerView = (RecyclerView) inputObj[2];
                placePhotoAdapter = (PlacePhotoAdapter) inputObj[3];
                placePhotoList =(List<PlacePhotoItem>) inputObj[4];

                googlePlacesList = placePhotoJsonParser.parse(googlePlacesJson);


        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<String> photoDatalist) {
            if(photoDatalist == null){
                String defautPhoto = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATcAAACiCAMAAAATIHpEAAAAclBMVEX///9otEVms0Jjsj1fsTh1ulafzozC37ddsDTZ69KYyYRbrzL8/vxari74/PaDwGnp9OR9vWDx+O5wuFCs1JyIwnDU6MxWrSeiz5G22Kmw1qHN5MSTx33K48Btt0t3ulni8Nvs9ei83LBNqhSp0piOxXdbgDzvAAADVElEQVR4nO3da3OiMBiGYUzSikLAUhVFrdbq//+Ly0lRqy68u1PocF/TD21oZ8IzORCE1HEAAAAAAAAAAAAAAAAAAAAAAP/LavX88OaH6vHLBF/x7snhSfwV/VRVfpdQb58cfdPhj9Xkd/m07rD4LhgND+Pj7DiebnblwY1nJ21VrOsORvuOE+1D4xqttVJaG0+vs2HPV2bRdvW6S5vF5OgZNbikXbVMIzV+27XrrpXRrh58o0z6xWz62PBOaCXDZPrQ2nsY22DgzduuXleFJh/Oboa3vChrh/Gw7Qp2U5h3Ur2fz256q0nm4yxSu2+7il20zltbPvy/X7U4naRFs6zIPl+I9dK+GNu87OJ2fNXgTDawJXmR+9l2Nbsm8qrGFVz3Uz1Oj2/zJqieLcR66XjKyqyTj5uJwcySbXnYLNuuaLfsbNW89Lf5VFVFXtB2VTtl/fiC95o+tF3VLgnimrGlcyrL1MrQ1M7NcPVbqd1N07GOu5eV+rGlHZWZ4SSyqj7W92lgRdvZfbzU916sUv2otxPE1Nh4OxL9aXS01q772WNnbjbMx2m3m49fG0hXYlF+Y0mrPga3KpYIapBehlhdn9me51/dx49qXstJ1O6aXL4Va/ty2aVU2yfRgtPNInfUMLcXx3fL703bJ9ECcpMhNxlykyE3GXKTITcZcW7v5EZuzZGbDLnJiHP7IDdya47cZMhNRpzbgNzIrTn6qQztTUaa24DcyE2A3GTEuSlyI7fmyE2G3GTEuWlyI7fmyE3mX3I7/X4fc0vKk7dR49zKt7X6+Wx54OYnb8aNXl8oGtmmeOYw7uUj0iNjtLYzR5CbM421NranL7kF+8UhbzHNc3MmyWLZ+5dRBbnBITcpcpNpmFtvX5S51Sw3l9xK9FOZif17Wmfqre3qdkfY4AVUNl+pBKpucMq+tl3ZLvEXrk2dt7vIfypdFsYvbAN3IwgC563MyN0FZ354LvT7+N5kHafcvMstQMN7hbhEbjLkJkNuMuQmQ24y5CZDbjLkJkNuMuQmc4rIXn7EN7tXiEvT4nZStvdPpXwgopeb09QUeHnbsld7MZYPRFg2aHxsolxj4qRGIa6tlsPv/2nhbiEAAAAAAAAAAAAAAAAAAAAA4B/8AeMWMnCUj6kNAAAAAElFTkSuQmCC";
                PlacePhotoItem item = new PlacePhotoItem(defautPhoto.toString());
                placePhotoList.add(item);
                placePhotoAdapter.notifyDataSetChanged();

            }
            else{
                for (int i = 0; i < photoDatalist.size(); i++) {

                    String PhotoRef = photoDatalist.get(i);


                    //PlacePhotoItem t = new PlacePhotoItem("https://maps.googleapis.com/maps/api/place/photo?maxheight=300&photoreference=CmRaAAAAnJQKWy_RV47qt0CditR42wNGVgL8FbflhdBojoGgL4mAVlmXQ5FMT_RJuSj_G4pAx6UDGJNFF4o9jlBgIaNu0TP2TpN20F3Jovqbiq0VAsTasOOrUUHDgCeO_Pmhlh7xEhCaVoOu9DUJz2sHWb9jQ3chGhT-Jg1czkDyYSZxcluogdRipciCVQ&key=AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8");


                    StringBuilder photoURL =  new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxheight=300&photoreference=" );
                    photoURL.append(PhotoRef);
                    photoURL.append("&key=AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8");

                    PlacePhotoItem item = new PlacePhotoItem(photoURL.toString());
                    placePhotoList.add(item);

                    placePhotoAdapter.notifyDataSetChanged();
                }
            }


        }

}
