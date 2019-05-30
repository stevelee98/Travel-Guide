package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hoangviet.mytravelapp.Adapter.PlacePhotoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FestivalInfor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FestivalInfor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FestivalInfor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private TextView fesName;
    private TextView fesDesciptions;
    private TextView fesTime;

    private ImageView imageView;
    private List<String> imgList;

    private RecyclerView recyclerView;
    private PlacePhotoAdapter festivalPhotoAdapter;
    private List<PlacePhotoItem> list;

    private Toolbar toolbar;


    private OnFragmentInteractionListener mListener;

    public FestivalInfor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FestivalInfor.
     */
    // TODO: Rename and change types and number of parameters
    public static FestivalInfor newInstance(String param1, String param2) {
        FestivalInfor fragment = new FestivalInfor();
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
        view = inflater.inflate(R.layout.festival_infor, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.fes_toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        fesName =(TextView) view.findViewById(R.id.fes_name);
        fesTime =(TextView) view.findViewById(R.id.fes_time);
        fesDesciptions = (TextView) view.findViewById(R.id.fes_description);
        imageView = (ImageView) view.findViewById(R.id.fes_avatar);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fes_photo);

        Bundle bundle = getArguments();

        String get = bundle.getString("NAME");
        fesName.setText(get);
        get = bundle.getString("DAY");
        fesTime.setText(get);
        get = bundle.getString("DESCIPTION");
        fesDesciptions.setText(get);

        Objects.requireNonNull(((AppCompatActivity) (getActivity())).getSupportActionBar()).setTitle("Festival");

        int imgListSize = Integer.parseInt(bundle.getString("IMG_SIZE"));
        imgList = new ArrayList<>();
        for(int i = 0; i<imgListSize; i++){
            StringBuilder Type = new StringBuilder("IMG");
            Type.append(i);
            imgList.add(bundle.getString(Type.toString()));
        }

        Glide.with(getContext()).load(imgList.get(0).toString()).into(imageView);

        list = new ArrayList<>();
        festivalPhotoAdapter = new PlacePhotoAdapter(getContext(),list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(festivalPhotoAdapter);
        loadFestivalPhoto(imgListSize);
        return view;
    }

    void loadFestivalPhoto(int imgListSize){

        for(int i = 0; i<imgListSize; i++) {
            PlacePhotoItem festivalPhotoItem = new PlacePhotoItem(imgList.get(i).toString());
            list.add(festivalPhotoItem);
            festivalPhotoAdapter.notifyDataSetChanged();
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
