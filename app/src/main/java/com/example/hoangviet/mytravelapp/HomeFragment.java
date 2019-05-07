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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoangviet.mytravelapp.Adapter.HomeItemAdapter;
import com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase.Explore_Place;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private static final String GOOGLE_API_KEY = "AIzaSyC3OjUhJ2nBCSmOfVz4kIcWAuwI_kaxgF8";

    private ExpandableTextView cityInfor;
    private RecyclerView rvExplore;
    private RecyclerView rvFoodDrink;
    private RecyclerView rvEntertainment;
    private RecyclerView rvFestival;
    private RecyclerView rvShoppingMall;

    private HomeItemAdapter festivalAdapter;
    private HomeItemAdapter exploreAdapter;
    private HomeItemAdapter food_drinkAdapter;
    private HomeItemAdapter entertaimentAdapter;
    private HomeItemAdapter shoppingMallApdapter;

    private List<HomeItemList> festivalList;
    private List<HomeItemList> exploreList;
    private List<HomeItemList> food_drinkList;
    private List<HomeItemList> entertaimentList;
    private List<HomeItemList> shoppingMallList;

    private List<String> listTest;
    private ArrayAdapter arrayAdapter;

    private ImageView imageView;

    public View view;

    public TextView test;
    public int i = 0;

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
        imageView = (ImageView) view.findViewById(R.id.avatar);

//        Glide.with(getContext())
//                .load("http://vietnam-business-consulting.com/wp-content/uploads/2018/09/Saigon-in-the-morning.jpg")
//                .into(imageView);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) (getActivity())).getSupportActionBar()).setTitle("Sài Gòn travel");
//        textView = (TextView) toolbar.findViewById(R.id.txt_cityname);
//        textView.setText("Home");

//        cityInfor = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
//        cityInfor.setText(longtext);

        rvExplore = (RecyclerView) view.findViewById(R.id.rv_explore);
        rvFoodDrink = (RecyclerView) view.findViewById(R.id.rv_food_and_drink);
        rvEntertainment = (RecyclerView) view.findViewById(R.id.rv_entertainment);
        rvFestival = (RecyclerView) view.findViewById(R.id.rv_festival);
        rvShoppingMall = (RecyclerView) view.findViewById(R.id.rv_shopping_mall);


        festivalList = new ArrayList<>();
        exploreList = new ArrayList<>();
        entertaimentList = new ArrayList<>();
        food_drinkList = new ArrayList<>();
        shoppingMallList = new ArrayList<>();


        exploreAdapter = new HomeItemAdapter(getActivity(), exploreList);
        food_drinkAdapter = new HomeItemAdapter(getActivity(), food_drinkList);
        entertaimentAdapter = new HomeItemAdapter(getActivity(), entertaimentList);
        festivalAdapter = new HomeItemAdapter(getActivity(), festivalList);
        shoppingMallApdapter = new HomeItemAdapter(getActivity(), shoppingMallList);

        RecyclerView.LayoutManager layoutManagerExplore = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvExplore.setLayoutManager(layoutManagerExplore);
        rvExplore.setAdapter(exploreAdapter);

        RecyclerView.LayoutManager layoutManagerFoodDrink = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFoodDrink.setLayoutManager(layoutManagerFoodDrink);
        rvFoodDrink.setAdapter(food_drinkAdapter);

        RecyclerView.LayoutManager layoutManagerEnter = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvEntertainment.setLayoutManager(layoutManagerEnter);
        rvEntertainment.setAdapter(entertaimentAdapter);

        RecyclerView.LayoutManager layoutManagerFes = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFestival.setLayoutManager(layoutManagerFes);
        rvFestival.setAdapter(festivalAdapter);

        RecyclerView.LayoutManager layoutManagerShop = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvShoppingMall.setLayoutManager(layoutManagerShop);
        rvShoppingMall.setAdapter(shoppingMallApdapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mData = firebaseDatabase.getReference();


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

                        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                        Object[] toPass = new Object[6];

                        toPass[0] = 0;
                        toPass[1] = null;
                        toPass[2] = textSearchURL.toString();
                        toPass[3] = rvExplore;
                        toPass[4] = exploreAdapter;
                        toPass[5] = exploreList;
                        googlePlacesReadTask.execute(toPass);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        load(food_drinkList,rvFoodDrink,food_drinkAdapter, "restaurant");
        load(entertaimentList,rvEntertainment,entertaimentAdapter, "bar");
        load(shoppingMallList,rvShoppingMall,shoppingMallApdapter, "shopping_mall");
        return view;
    }



    private void loadFoodDrinkList() {
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("type=restaurant");
        //googlePlacesUrl.append("&keyword=ăn+uống");
        googlePlacesUrl.append("&location=10.773130,106.697942");
        googlePlacesUrl.append("&radius=" + 6000);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[6];
        toPass[0] = 0;
        toPass[1] = null;
        toPass[2] = googlePlacesUrl.toString();
        toPass[3] = rvFoodDrink;
        toPass[4] = food_drinkAdapter;
        toPass[5] = food_drinkList;

        googlePlacesReadTask.execute(toPass);

    }
    private void loadEntertainmentList() {
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("type=bar");
        //googlePlacesUrl.append("&keyword=ăn+uống");
        googlePlacesUrl.append("&location=10.773130,106.697942");
        googlePlacesUrl.append("&radius=" + 6000);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[6];
        toPass[0] = 0;
        toPass[1] = null;
        toPass[2] = googlePlacesUrl.toString();
        toPass[3] = rvEntertainment;
        toPass[4] = entertaimentAdapter;
        toPass[5] = entertaimentList;

        googlePlacesReadTask.execute(toPass);

    }
    private void load(List<HomeItemList> list,RecyclerView rv, HomeItemAdapter homeItemAdapter, String type) {
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("type=" + type);
        googlePlacesUrl.append("&location=10.773130,106.697942");
        googlePlacesUrl.append("&radius=" + 6000);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[6];
        toPass[0] = 0;
        toPass[1] = null;
        toPass[2] = googlePlacesUrl.toString();
        toPass[3] = rv;
        toPass[4] = homeItemAdapter;
        toPass[5] = list;

        googlePlacesReadTask.execute(toPass);

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
