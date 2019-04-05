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
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;
    public TextView textView;


    private ExpandableTextView cityInfor;
    private RecyclerView recyclerView;
    private FestivalAdapter festivalAdapter;
    private List<FestivalItem> festivalItemList;
    public View view;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = (View) inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
//        textView = (TextView) toolbar.findViewById(R.id.txt_cityname);
//        textView.setText("Home");

//        cityInfor = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
//        cityInfor.setText(longtext);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_festival);
        festivalItemList = new ArrayList<>();
        festivalAdapter = new FestivalAdapter(getActivity(), festivalItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(festivalAdapter);
        prepareFes();
        return view;
    }


    private void prepareFes() {
        int[] fes = new int[] {
                R.drawable.fes_denlong,
                R.drawable.fes_dentroi,
                R.drawable.fes_halloween,
                R.drawable.fes_hoadang,
                R.drawable.fes_other,
                R.drawable.xemthem
        };

        FestivalItem a = new FestivalItem("Lễ hội đèn lồng", fes[0]);
        festivalItemList.add(a);

        a = new FestivalItem("Lễ hội hoa đăng", fes[3]);
        festivalItemList.add(a);

        a = new FestivalItem("Lễ hội halloween", fes[2]);
        festivalItemList.add(a);

        a = new FestivalItem("Lễ hội đèn trời", fes[1]);
        festivalItemList.add(a);

        a = new FestivalItem("Lễ hội", fes[4]);
        festivalItemList.add(a);
        a = new FestivalItem("", fes[5]);
        festivalItemList.add(a);


        festivalAdapter.notifyDataSetChanged();
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
