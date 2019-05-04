package com.example.hoangviet.mytravelapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvenientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConvenientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvenientFragment extends Fragment implements MapFragment.OnFragmentInteractionListener, LocationListener,
        OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView currentLocation;
    public View view;
    private Toolbar toolbar;
    private CardView cvEmergency;
    private CardView cvMedical;
    private CardView cvShoppingMall;
    private CardView cvBarPub;
    private CardView cvEntertaiment;
    private CardView cvNearestHotel;
    private CardView cvAtm;
    private CardView cvPublicTransport;
    private CardView cvConvenientStore;
    private CardView cvGasStation;
    private CardView cvParking;
    private CardView cvCarRepair;
    private CardView cvFoodDrink;

    double latitude = 0;
    double longitude = 0;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 14;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private GoogleApiClient googleApiClient;
    private static final String KEY_LOCATION = "location";


    private static final String TAG_RESULT = "predictions";

    private OnFragmentInteractionListener mListener;

    public ConvenientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConvenientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConvenientFragment newInstance(String param1, String param2) {
        ConvenientFragment fragment = new ConvenientFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_convenient, container, false);

        //đặt tiêu đề cho toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_convenient);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) (getActivity())).getSupportActionBar()).setTitle("Explore around you");

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(getActivity());

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        currentLocation = (TextView) view.findViewById(R.id.txt_current_location);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.

                        mLastKnownLocation = task.getResult();

                        latitude = mLastKnownLocation.getLatitude();
                        longitude = mLastKnownLocation.getLongitude();

                        Geocoder geocoder = new Geocoder(getContext().getApplicationContext());
                        List<Address> list = new ArrayList<>();
                        try {
                            list = geocoder.getFromLocation(latitude, longitude, 2);
                        } catch (IOException e) {
                            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
                        }

                        if (list.size() > 0) {
                            Address addresses = list.get(0);

                            Log.d(TAG, "geoLocate: found a location: " + addresses.toString());
                            String address = list.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = list.get(0).getLocality();
                            String country = list.get(0).getCountryName();

                            currentLocation.setText(address);
                        }

                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                    }
                }
            });


        cvEmergency = (CardView) view.findViewById(R.id.cv_emergency);
        cvMedical = (CardView) view.findViewById(R.id.cv_medical);
        cvShoppingMall = (CardView) view.findViewById(R.id.cv_shopping);
        cvBarPub = (CardView) view.findViewById(R.id.cv_bar_pub);
        cvNearestHotel = (CardView) view.findViewById(R.id.cv_nearest_hotel);
        cvAtm = (CardView) view.findViewById(R.id.cv_atm);
        cvConvenientStore = (CardView) view.findViewById(R.id.cv_conveniente_store);
        cvEntertaiment = (CardView) view.findViewById(R.id.cv_entertaiment);
        cvCarRepair = (CardView) view.findViewById(R.id.cv_car_repair);
        cvPublicTransport = (CardView) view.findViewById(R.id.cv_public_transport);
        cvGasStation = (CardView) view.findViewById(R.id.cv_gas_station);
        cvParking = (CardView) view.findViewById(R.id.cv_parking);
        cvFoodDrink = (CardView) view.findViewById(R.id.cv_food_drink);


        //tìm đồn cảnh sát
        cvEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("police|fire_station");
            }
        });

        //tìm chăm sóc y tế
        cvMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("hospital|pharmacy|doctor");
            }
        });

        //tìm chợ hoặc siêu thị
        cvShoppingMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("shopping_mall|supermarket|department_store");
            }
        });

        //tìm nơi ăn chơi buổi tối
        cvBarPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("bar|night_club|casino");
            }
        });

        //tìm công viên giải trí
        cvEntertaiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("amusement_park|park|rv_park");
            }
        });

        //tìm atm, ngân hàng
        cvAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("atm|bank");
            }
        });

        //tìm khách sạn
        cvNearestHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("lodging");
            }
        });

        //tìm cửa hàng tiện lợi
        cvConvenientStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("convenience_store");
            }
        });

        //tìm trạm xăng
        cvGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("gas_station");
            }
        });

        //tìm chỗ đậu xe
        cvParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("parking");
            }
        });

        //tìm nơi sửa xe
        cvCarRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("car_repair");
            }
        });


        //tìm nơi ăn uống
        cvFoodDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap("restaurant");
            }
        });

        return view;
    }

    public void loadMap(String type){
        Bundle bundle;
        MapFragment mapFragment;

        bundle = new Bundle();
        mapFragment = new MapFragment();

        bundle.putString("TYPE", type);
        mapFragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        getDeviceLocation();
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

                            geoLocate(latitude,longitude);

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void geoLocate(double latitude, double longitude) {
        Log.d(TAG, "geoLocate: geolocating");

        if (mLocationPermissionGranted) {

            Geocoder geocoder = new Geocoder(getContext().getApplicationContext());
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            }

            if (list.size() > 0) {
                Address addressName = list.get(0);

                Log.d(TAG, "geoLocate: found a location: " + addressName.toString());
                Toast.makeText(getActivity(), addressName.toString(), Toast.LENGTH_SHORT).show();
                currentLocation.setText(addressName.toString());
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        mLastKnownLocation = location;

        LatLng latLng = new LatLng(latitude, longitude);

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
                }
                else{

                    Toast.makeText(getActivity(), "location is disable", Toast.LENGTH_LONG).show();
                }
            }
        }
        updateLocation();
    }
    private void updateLocation() {
        try {
            if (mLocationPermissionGranted) {

            } else {
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
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
    public void onFragmentInteraction(Uri uri) {

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
