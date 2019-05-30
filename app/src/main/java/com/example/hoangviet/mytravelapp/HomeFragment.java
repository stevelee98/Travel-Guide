package com.example.hoangviet.mytravelapp;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.example.hoangviet.mytravelapp.Adapter.FestivalAdapter;
import com.example.hoangviet.mytravelapp.Adapter.HomeItemAdapter;
import com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase.Explore_Place;
import com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase.FestivalItem;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.example.hoangviet.mytravelapp.google.PlacesDisplayTask;
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
    public RecyclerView rvExplore;
    public RecyclerView rvFoodDrink;
    public RecyclerView rvEntertainment;
    public RecyclerView rvShoppingMall;
    public RecyclerView rvFestival;

    private FestivalAdapter festivalAdapter;
    public HomeItemAdapter exploreAdapter;
    public HomeItemAdapter food_drinkAdapter;
    public HomeItemAdapter entertaimentAdapter;
    public HomeItemAdapter shoppingMallApdapter;

    public List<FestivalItem> festivalList;
    public List<HomeItemList> exploreList;
    public List<HomeItemList> food_drinkList;
    public List<HomeItemList> entertaimentList;
    public List<HomeItemList> shoppingMallList;

    public List<String> listTest;
    public ArrayAdapter arrayAdapter;

    private ImageView imageView;

    public View view;
    AlertDialog dialog;

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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mData = firebaseDatabase.getReference();

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) (getActivity())).getSupportActionBar()).setTitle("Sài Gòn travel");

        cityInfor = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        cityInfor.setText(getResources().getString(R.string.about_city));

        rvExplore = (RecyclerView) view.findViewById(R.id.rv_explore);
        rvFoodDrink = (RecyclerView) view.findViewById(R.id.rv_food_and_drink);
        rvEntertainment = (RecyclerView) view.findViewById(R.id.rv_entertainment);
        rvShoppingMall = (RecyclerView) view.findViewById(R.id.rv_shopping_mall);
        rvFestival = (RecyclerView) view.findViewById(R.id.rv_festival);


        festivalList = new ArrayList<>();
        exploreList = new ArrayList<>();
        entertaimentList = new ArrayList<>();
        shoppingMallList = new ArrayList<>();
        food_drinkList = new ArrayList<>();


        food_drinkAdapter = new HomeItemAdapter(getContext(), food_drinkList);
        food_drinkAdapter.setOnItemClickListener(new HomeItemAdapter.OnItemClickListener() {
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
        RecyclerView.LayoutManager layoutManagerFoodDrink = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFoodDrink.setLayoutManager(layoutManagerFoodDrink);
        rvFoodDrink.setAdapter(food_drinkAdapter);


        entertaimentAdapter = new HomeItemAdapter(getContext(), entertaimentList);
        RecyclerView.LayoutManager layoutManagerEn = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvEntertainment.setLayoutManager(layoutManagerEn);
        rvEntertainment.setAdapter(entertaimentAdapter);


        shoppingMallApdapter = new HomeItemAdapter(getActivity(), shoppingMallList);
        RecyclerView.LayoutManager layoutManagerShop = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvShoppingMall.setLayoutManager(layoutManagerShop);
        rvShoppingMall.setAdapter(shoppingMallApdapter);


        festivalAdapter = new FestivalAdapter(getActivity(), festivalList);
        festivalAdapter.setOnItemClickListener(new FestivalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle1 = new Bundle();
                FestivalInfor festivalInfor = new FestivalInfor();

                bundle1.putString("NAME", festivalList.get(position).getName().toString());
                bundle1.putString("DAY", festivalList.get(position).getDay().toString());
                bundle1.putString("DESCIPTION", festivalList.get(position).getDescription().toString());
                bundle1.putString("IMG_SIZE", Integer.toString(festivalList.get(position).getImgList().size()));

                for (int i = 0; i < festivalList.get(position).getImgList().size(); i++) {
                    StringBuilder Type = new StringBuilder("IMG");
                    Type.append(Integer.toString(i));
                    bundle1.putString(Type.toString(), festivalList.get(position).getImgList().get(i).toString());
                }
                festivalInfor.setArguments(bundle1);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, festivalInfor);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        RecyclerView.LayoutManager layoutManagerFes = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFestival.setLayoutManager(layoutManagerFes);
        rvFestival.setAdapter(festivalAdapter);


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


        load(food_drinkList, rvFoodDrink, food_drinkAdapter, "restaurant");
        load(entertaimentList, rvEntertainment, entertaimentAdapter, "bar");
        load(shoppingMallList, rvShoppingMall, shoppingMallApdapter, "shopping_mall");


        exploreAdapter = new HomeItemAdapter(getActivity(), exploreList);
        RecyclerView.LayoutManager layoutManagerEx = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvExplore.setLayoutManager(layoutManagerEx);
        rvExplore.setAdapter(exploreAdapter);
        mData.child("hochiminh_city").child("festival").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    FestivalItem festivalItem = postSnapshot.getValue(FestivalItem.class);

                    List<String> photoList = new ArrayList<>();
                    photoList = (ArrayList<String>) postSnapshot.child("photos").getValue();

                    festivalItem.setImgList(photoList);

                    festivalList.add(festivalItem);
                    festivalAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void load(List<HomeItemList> list, RecyclerView rv, HomeItemAdapter homeItemAdapter, String type) {
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("type=" + type);
        googlePlacesUrl.append("&location=10.773130,106.697942");
        googlePlacesUrl.append("&radius=" + 6000);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask(getContext());
        Object[] toPass = new Object[6];
        toPass[0] = 0;
        toPass[1] = null;
        toPass[2] = googlePlacesUrl.toString();
        toPass[3] = rv;
        toPass[4] = homeItemAdapter;
        toPass[5] = list;

        placesDisplayTask.execute(toPass);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
