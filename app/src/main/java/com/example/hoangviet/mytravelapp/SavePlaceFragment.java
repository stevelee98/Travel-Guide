package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hoangviet.mytravelapp.Adapter.SaveItemPlaceAdapter;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SavePlaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SavePlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavePlaceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  double lat;
    private double lng;
    private TextView placeName;
    private TextView placeRating;
    private TextView openNow;

    private ImageView imageView;

    private String placeID;
    private RatingBar placeRatingBar;
    private View view;

    private RecyclerView recyclerView;
    private List<HomeItemList> lists = new ArrayList<>();
    private SaveItemPlaceAdapter saveItemPlaceAdapter;

    private static final String GOOGLE_API_KEY = "AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8";

    private OnFragmentInteractionListener mListener;

    public SavePlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavePlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavePlaceFragment newInstance(String param1, String param2) {
        SavePlaceFragment fragment = new SavePlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            lists = savedInstanceState.getParcelableArrayList("LIST");
        }
        catch (NullPointerException e){
            Log.e("Exception: ", e.getMessage());
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_save_place, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_save_place);

        placeName = (TextView) view.findViewById(R.id.save_place_name);
        placeRating = (TextView) view.findViewById(R.id.save_place_num_star);
        imageView = (ImageView) view.findViewById(R.id.save_place_img);
        openNow = (TextView) view.findViewById(R.id.save_place_open_now);
        placeRatingBar = (RatingBar) view.findViewById(R.id.save_place_ratingbar);

        try{
            final Bundle bundle = getArguments();

            StringBuilder photoRequest = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=");
            String ptRef = bundle.getString("PHOTO_REFER");
            photoRequest.append(ptRef);
            photoRequest.append("&key=" + GOOGLE_API_KEY);

            String name = bundle.getString("NAME");

            String numStar = bundle.getString("RATING");

            String open = bundle.getString("OPEN_NOW");

            placeID = bundle.getString("PLACE_ID");
            lat = bundle.getDouble("LAT");
            lng = bundle.getDouble("LNG");

            HomeItemList item = new HomeItemList(name, placeID,"",numStar,"",open,"",photoRequest.toString(),lat,lng);
            lists.add(item);
        }catch (NullPointerException e) {
            Log.e("Exception: ", e.getMessage());
        }
        saveItemPlaceAdapter = new SaveItemPlaceAdapter(getContext(), lists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(saveItemPlaceAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("LIST", (ArrayList<? extends Parcelable>) lists);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try{
            lists = savedInstanceState.getParcelableArrayList("LIST");
        }
        catch (NullPointerException e){
            Log.e("Exception: ", e.getMessage());
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
