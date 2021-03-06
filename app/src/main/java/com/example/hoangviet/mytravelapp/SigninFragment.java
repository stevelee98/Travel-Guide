package com.example.hoangviet.mytravelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.Objects;

import android.view.LayoutInflater;
import android.util.Log;

import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;

import com.example.hoangviet.mytravelapp.UI.Dialog.SimpleMessage;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link SigninFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link SigninFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class SigninFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View view;

    private Button btnSignUp;
    private Button btnSignIn;

    private LoginButton loginButton;
    private CallbackManager mCallbackManager;


    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;
    private GoogleSignInClient mGoogleSignInClient;

    private ProgressDialog progressBar;

    private int RC_SIGN_IN = 123;

    public SigninFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the
     * provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mListener.handleSignInResult(task);
        }
        this.mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signin, container, false);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());

        this.loginButton = view.findViewById(R.id.fb_login_button);

        this.mCallbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email","public_profile");
        this.loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                mListener.onSignInFacebookStart();
                mListener.signInByFacebook(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK", "facebook:onCancel");
                SimpleMessage errorMessage = new SimpleMessage();
                errorMessage.setValue("Khong dang nhap a",getResources().getString(R.string.sign_up_error_message));
                errorMessage.show(getFragmentManager(),"signup_error");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FACEBOOK", "facebook:onError", error);
                SimpleMessage errorMessage = new SimpleMessage();
                errorMessage.setValue("Dang nhap bi loi a",getResources().getString(R.string.sign_up_error_message));
                errorMessage.show(getFragmentManager(),"signup_error");
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);

        SignInButton signInButton = view.findViewById(R.id.btn_signup_google);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        return view;
    }
    private void showMessage(){
        SimpleMessage errorMessage = new SimpleMessage();
        errorMessage.setValue(getResources().getString(R.string.sign_up_error_title),getResources().getString(R.string.sign_up_error_message));
        errorMessage.show(getFragmentManager(),"signup_error");
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnSignIn = (Button) view.findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInClick();
            }
        });
        btnSignUp = (Button) view.findViewById(R.id.btn_signup);
        final MainActivity activity = new MainActivity();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClick();
            }
        });
    }
    public void onSignInClick(){
            EditText emailEditText = view.findViewById(R.id.email_signin);
            EditText passwordEditText = view.findViewById(R.id.password);
            if(this.isEmpty(emailEditText) || this.isEmpty(passwordEditText)){
                SimpleMessage errorMessage = new SimpleMessage();
                errorMessage.setValue(getContext().getResources().getString(R.string.simple_dialog_tilte),getContext().getResources().getString(R.string.simple_dialog_message));
                errorMessage.show(getFragmentManager(),"signin_error_message");
            } else {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                mListener.signIn(email, password);
            }
    }
    public void onSignUpClick(){
        FragmentTransaction transaction = (Objects.requireNonNull(getActivity())).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,new SignUpFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    protected boolean isEmpty(EditText editText) {
        return (editText.getText().toString().equals(""));
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction();
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this fragment
     * to allow an interaction in this fragment to be communicated to the activity
     * and potentially other fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSignInFacebookStart();
        void signIn(String email, String password);
        void signInByFacebook(AccessToken accessToken);
        void handleSignInResult(Task<GoogleSignInAccount> completedTask);
    }
}
