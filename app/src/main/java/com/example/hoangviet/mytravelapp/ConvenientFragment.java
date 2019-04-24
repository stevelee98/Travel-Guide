package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvenientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConvenientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvenientFragment extends Fragment implements MapFragment.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        view  = inflater.inflate(R.layout.fragment_convenient, container, false);

        //đặt tiêu đề cho toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_convenient);
        ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity)(getActivity())).getSupportActionBar()).setTitle("Explore around you");


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
