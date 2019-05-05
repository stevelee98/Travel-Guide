package com.example.hoangviet.mytravelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hoangviet.mytravelapp.Models.User;
import com.example.hoangviet.mytravelapp.services.crud.UserService;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profife_notLogin_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profife_notLogin_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profife_notLogin_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View view;
    private Button btnProfile;
    private OnFragmentInteractionListener mListener;
    private Button btnLanguage;
    private Button btnSignIn;
    private Button btnSignOut;
    private ImageView avatarView;
    private ProgressDialog progressBar;

    public Profife_notLogin_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profife_notLogin_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profife_notLogin_Fragment newInstance(String param1, String param2) {
        Profife_notLogin_Fragment fragment = new Profife_notLogin_Fragment();
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
        view = inflater.inflate(R.layout.fragment_profife_not_login_, container, false);
        avatarView = view.findViewById(R.id.img_avatar);
        btnProfile = (Button) view.findViewById(R.id.btn_profile);
        btnLanguage = view.findViewById(R.id.btn_language);
        btnSignOut = view.findViewById(R.id.btn_sign_out);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_container,new SigninFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_container,new SelectLanguageDialog());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        try {
            progressBar = new ProgressDialog(getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("Please wait...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            progressBar.show();
            getCurrentUser();

        } catch (Exception err){
            progressBar.cancel();
        } finally {

        }
        return view;
    }
    public void signOut(){
        boolean isSignOut = mListener.signOut();

        if(isSignOut == true){

            this.btnProfile.setText(R.string.sign_in);
            this.btnSignOut.setVisibility(View.GONE);
            this.avatarView.setImageResource(R.drawable.user);
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.frame_container,new SigninFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        } else {

        }
    }

    public void getCurrentUser(){
        FirebaseUser userInfo = mListener.getCurrentUser();
        String uid = userInfo.getUid();
        UserService userService =  UserService.getInstance();
        userService.readData(uid, new UserService.FirebaseCallback() {
            @Override
            public void onGetUserData(User user) {
                btnProfile.setText(user.getUsername());
                String avatar = user.getAvatar();
                Glide.with(getActivity())
                        .load(avatar)
                        .into(avatarView);
                btnProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBtnProfileClick();
                    }
                });
                btnSignOut.setVisibility(View.VISIBLE);
                progressBar.cancel();
            }
        });

    }
    public void onBtnProfileClick(){

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

        FirebaseUser getCurrentUser();
        boolean signOut();

    }
}
