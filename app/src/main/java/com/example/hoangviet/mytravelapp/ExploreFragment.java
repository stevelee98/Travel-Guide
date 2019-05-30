package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.hoangviet.mytravelapp.Adapter.CustomSimpleListViewAdapter;
import com.example.hoangviet.mytravelapp.Adapter.HomeItemAdapter;
import com.example.hoangviet.mytravelapp.Conect.Http;
import com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase.Explore_Place;
import com.example.hoangviet.mytravelapp.ItemsList.HandBookItem;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.example.hoangviet.mytravelapp.Parser.XMLDOMParser;
import com.example.hoangviet.mytravelapp.google.PlacesDisplayTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExploreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String GOOGLE_API_KEY = "AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8";

    View view;
    NonScrollListView lvHandbook;
    RecyclerView rvExplore;
    HomeItemAdapter exploreAdapter;
    List<HomeItemList> exploreList;

    List<HandBookItem> HandbookTitleList;
    CustomSimpleListViewAdapter arrayAdapter;

    private OnFragmentInteractionListener mListener;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mData = firebaseDatabase.getReference();

        lvHandbook  = (NonScrollListView) view.findViewById(R.id.lv_handbook);
        lvHandbook.setScrollContainer(false);
        HandbookTitleList = new ArrayList<>();

        arrayAdapter = new CustomSimpleListViewAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), R.layout.item_handbook,HandbookTitleList);
        lvHandbook.setAdapter(arrayAdapter);
        new ReadRSS().execute("https://www.vntrip.vn/cam-nang/du-lich-tp-ho-chi-minh/feed");

        lvHandbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                HandBookViewFragment handBookViewFragment = new HandBookViewFragment();

                String link =  HandbookTitleList.get(position).getLink().toString();
                bundle.putString("LINK",link);
                handBookViewFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, handBookViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        rvExplore = (RecyclerView) view.findViewById(R.id.rv_explore_place);
        exploreList = new ArrayList<>();

        mData.child("hochiminh_city").child("explore_place").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Explore_Place explore_place = postSnapshot.getValue(Explore_Place.class);
                    StringBuilder textSearchURL =
                            new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=");
                    textSearchURL.append(explore_place.getName().toString());
                    textSearchURL.append("&inputtype=textquery&fields=" +
                            "place_id,photos,formatted_address,name,rating,user_ratings_total,opening_hours,geometry&key=");
                    textSearchURL.append(GOOGLE_API_KEY);

                    //test.setText(textSearchURL.toString());

                    PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask(getContext());
                    Object[] toPass = new Object[6];

                    toPass[0] = 0;
                    toPass[1] = null;
                    toPass[2] = textSearchURL.toString();
                    toPass[3] = rvExplore;
                    toPass[4] = exploreAdapter;
                    toPass[5] = exploreList;
                    placesDisplayTask.execute(toPass);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exploreAdapter = new HomeItemAdapter(getActivity(), exploreList);
        exploreAdapter.setOnItemClickListener(new HomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, List<HomeItemList> list) {
                Bundle bundle1 = new Bundle();
                PlaceInfor placeInfor = new PlaceInfor();
                bundle1.putDouble("LAT", list.get(position).getLatitude());
                bundle1.putDouble("LNG", list.get(position).getLongtitude());
                bundle1.putString("NAME", list.get(position).getItemName().toString());
                bundle1.putString("RATING", list.get(position).getItemNumStar().toString());
                bundle1.putString("ADDRESS", list.get(position).getAddress().toString());
                bundle1.putString("PHOTO_REFER", list.get(position).getPhotoReference().toString());
                bundle1.putString("TOTAL_RATING", list.get(position).getUserTotalRating().toString());
                bundle1.putString("PLACE_ID", list.get(position).getItemPlaceID().toString());

                placeInfor.setArguments(bundle1);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, placeInfor);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        RecyclerView.LayoutManager layoutManagerEx = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvExplore.setLayoutManager(layoutManagerEx);
        rvExplore.setAdapter(exploreAdapter);



        return view;
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

    private class ReadRSS extends AsyncTask<String, Void, String> {

        Http http = new Http();
        String RSSurl;
        String resutl = null;
        @Override
        protected String doInBackground(String... strings) {
            try {

                resutl = http.read(strings[0].toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resutl;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);

            NodeList nodeList = document.getElementsByTagName("item");

            String tieude = "";
            String link = "";

            for(int i = 0; i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                tieude = parser.getValue(element,"title");
                link = parser.getValue(element, "link");
                HandBookItem handBookItem = new HandBookItem(tieude,link);
                HandbookTitleList.add(handBookItem);
                arrayAdapter.notifyDataSetChanged();
            }
            arrayAdapter.notifyDataSetChanged();
            //Toast.makeText(getActivity(),tieude.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
