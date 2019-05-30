package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hoangviet.mytravelapp.Adapter.PlacePhotoAdapter;
import com.example.hoangviet.mytravelapp.google.PlacePhotoDisplayTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaceInfor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlaceInfor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceInfor extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final int PLACE_PHOTO = 1;
    private static final String GOOGLE_API_KEY = "AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8";


    double latitude = 0;
    double longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    Spinner mSprPlaceType;
    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;

    private RecyclerView recyclerView;
    private PlacePhotoAdapter placePhotoAdapter;
    private List<PlacePhotoItem> list;
    private GoogleMap mMap;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private static final int DEFAULT_ZOOM = 14;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View view;

    private TextView placeName;
    private TextView placeAddress;
    private TextView placeRating;
    private TextView placeUserTotalRating;

    private ImageView imageView;

    private RatingBar placeRatingBar_1;
    private ImageButton savePlace;
    private ImageButton direction;

    private String placeID;
    private  double lat;
    private double lng;

    private OnFragmentInteractionListener mListener;

    public PlaceInfor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceInfor.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceInfor newInstance(String param1, String param2) {
        PlaceInfor fragment = new PlaceInfor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_infor, container, false);

        savePlace = (ImageButton) view.findViewById(R.id.save_place_button);
        direction = (ImageButton) view.findViewById(R.id.direction_button);

        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(getActivity());

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


        placeName = (TextView) view.findViewById(R.id.place_name);
        placeAddress = (TextView) view.findViewById(R.id.place_address);
        placeRating = (TextView) view.findViewById(R.id.place_num_star);
        placeUserTotalRating = (TextView) view.findViewById(R.id.place_user_total_rating);
        imageView = (ImageView) view.findViewById(R.id.img_view);

        placeRatingBar_1 = (RatingBar) view.findViewById(R.id.place_ratingBar);

        final Bundle bundle = getArguments();

        StringBuilder photoRequest = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=");
        final String ptRef = bundle.getString("PHOTO_REFER");

        photoRequest.append(ptRef);
        photoRequest.append("&key=" + GOOGLE_API_KEY);

        //load avatar của địa điểm
        Glide.with(this).load(photoRequest.toString()).into(imageView);

        final String name = bundle.getString("NAME");
        placeName.setText(name.toString());

        final String address = bundle.getString("ADDRESS");
        placeAddress.setText(address.toString());

        final String numStar = bundle.getString("RATING");
        placeRating.setText(numStar.toString());

        placeRatingBar_1.setRating(Float.parseFloat(numStar.toString()));

        String totalRating = bundle.getString("TOTAL_RATING");
        placeUserTotalRating.setText(totalRating.toString());
        final String openNow = bundle.getString("OPEN_NOW");


        placeID = bundle.getString("PLACE_ID");

        lat = bundle.getDouble("LAT");
        lng = bundle.getDouble("LNG");

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_place_image);

        list = new ArrayList<>();
        placePhotoAdapter = new PlacePhotoAdapter(getActivity(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(placePhotoAdapter);


        //load hình ảnh địa điểm
        StringBuilder placePhotoUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");

        placePhotoUrl.append(placeID.toString());
        placePhotoUrl.append("&fields=photo");
        placePhotoUrl.append("&key=" + GOOGLE_API_KEY);

        PlacePhotoDisplayTask placePhotoDisplayTask = new PlacePhotoDisplayTask();
        Object[] toPass = new Object[5];
        toPass[0] = PLACE_PHOTO;
        toPass[1] = placePhotoUrl.toString();
        toPass[2] = recyclerView;
        toPass[3] = placePhotoAdapter;
        toPass[4] = list;
        placePhotoDisplayTask.execute(toPass);

        savePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePlaceFragment savePlaceFragment = new SavePlaceFragment();
                Bundle sendBundle = new Bundle();

                sendBundle.putString("NAME", name.toString());
                sendBundle.putString("RATING",numStar.toString() );
                sendBundle.putString("PHOTO_REFER", ptRef.toString());
                if( bundle.getString("OPEN_NOW") != null){
                    sendBundle.putString("OPEN_NOW", openNow.toString());
                }

                sendBundle.putString("PLACE_ID",placeID.toString());
                savePlaceFragment.setArguments(sendBundle);
                Toast.makeText(getContext(), "save",Toast.LENGTH_LONG).show();

            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Direction", Toast.LENGTH_LONG).show();
//                Uri navigationIntentUri = Uri.parse("geo:0,0?q="+lat+","+lng+"+airport"+")");//creating intent with latlng
//                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, navigationIntentUri);
//                startActivity(mapIntent);
                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lng);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.

                            mLastKnownLocation = task.getResult();

                            latitude = mLastKnownLocation.getLatitude();
                            longitude = mLastKnownLocation.getLongitude();

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        mLastKnownLocation = location;

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            mLocationPermissionGranted = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {

                    Toast.makeText(getActivity(), "location is disable", Toast.LENGTH_LONG).show();
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLoaded() {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}