package com.example.hoangviet.mytravelapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.hoangviet.mytravelapp.Models.User;
import com.example.hoangviet.mytravelapp.UI.Dialog.SimpleMessage;
import com.example.hoangviet.mytravelapp.auth.FirebaseAuthentication;
import com.example.hoangviet.mytravelapp.helper.BottomNavigationBehavior;
import com.example.hoangviet.mytravelapp.services.crud.UserService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        ConvenientFragment.OnFragmentInteractionListener,
        Profife_notLogin_Fragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener ,
        SignUpFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        SearchView.OnQueryTextListener,
        SelectLanguageDialog.OnFragmentInteractionListener{



    private static final String TAG = "MainActivity";
    private ProgressDialog progressBar;
    private int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;

    //public SearchVpubliciew searchView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FacebookSdk.sdkInitialize(getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuthentication.mAuth.addAuthStateListener(FirebaseAuthentication.mAuthListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        fragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        FirebaseAuthentication.getInstance().handleGoogleSignInResult(completedTask, new FirebaseAuthentication.AddOnSignInByGoogleComplete() {
            @Override
            public void onSignUpComplete(Boolean isSuccess, GoogleSignInAccount account) {
                FirebaseUser user = FirebaseAuthentication.getInstance().getCurrentUser();
                User userModel = new User(user.getDisplayName(),user.getEmail(), "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png",user.getUid());
                UserService.getInstance().createUser(userModel);
                Fragment fragment;
                fragment = new Profife_notLogin_Fragment();
                loadFragment(R.id.frame_container, fragment);
            }

            @Override
            public void onSignUpError() {
                SimpleMessage errorMessage = new SimpleMessage();
                errorMessage.setValue(getResources().getString(R.string.sign_up_error_title),getResources().getString(R.string.sign_up_error_message));
                errorMessage.show(getSupportFragmentManager(),"signup_error");
            }
        });
    }


    public FirebaseUser getCurrentUser(){
        return FirebaseAuthentication.getInstance().getCurrentUser();
    }

    public boolean signOut(){

        try {
            LoginManager.getInstance().logOut();
            mGoogleSignInClient.signOut();
        } catch(Exception err){
            mGoogleSignInClient.signOut();
        } finally {
            Boolean isSignOut = FirebaseAuthentication.getInstance().signOut();
            if(isSignOut) {
                finish();
                Intent main = new Intent(MainActivity.this, MainActivity.class);
                startActivity(main);
            } else {
                return false;
            }
        }
        return true;
    }
    public void onSelectLanguageFinish(String language){
        Locale locale;
        if(language == "en_US"){
            locale = new Locale("en","US");
        } else {
            locale = new Locale("vi", "VN");
        }

        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        Configuration configuration = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        getBaseContext().getResources().updateConfiguration(configuration, displayMetrics);
        Intent main = new Intent(MainActivity.this, MainActivity.class);
        startActivity(main);
        finish();

    }


    public void signIn(String email, String password){
        FirebaseAuthentication.getInstance().SignInWithEmailAndPassword(email, password, new FirebaseAuthentication.AddOnSignInComplete() {
            @Override
            public void onSignInComplete(Boolean isSuccess) {
                Fragment fragment;
                if(isSuccess){
                    fragment = new Profife_notLogin_Fragment();
                    loadFragment(R.id.frame_container, fragment);
                } else {
                    fragment = new SigninFragment();
                    loadFragment(R.id.frame_container, fragment);
                }
            }

        });
    }

    public void onSignInFacebookStart(){
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }

    public void signInByFacebook(AccessToken accessToken){
        FirebaseAuthentication.getInstance().handleFacebookAccessToken(accessToken, new FirebaseAuthentication.AddOnSignInByFacebookComplete() {
            @Override
            public void onSignUpComplete(Boolean isSuccess) {
                Fragment fragment;
                if(isSuccess){
                    FirebaseUser user = FirebaseAuthentication.getInstance().getCurrentUser();
                    User userModel = new User(user.getDisplayName(),user.getEmail(), "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png",user.getUid());
                    UserService.getInstance().createUser(userModel);
                    fragment = new Profife_notLogin_Fragment();
                    loadFragment(R.id.frame_container, fragment);
                    progressBar.cancel();
                } else {
                    progressBar.cancel();
                    SimpleMessage errorMessage = new SimpleMessage();
                    errorMessage.setValue("Dang nhap bi loi",getResources().getString(R.string.sign_up_error_message));
                    errorMessage.show(getSupportFragmentManager(),"signup_error");
                    fragment = new SigninFragment();
                    loadFragment(R.id.frame_container, fragment);

                }
            }
        });
    }

    public void createAccount(final String email, String password, final String username) {

        FirebaseAuthentication.getInstance().SignUpWithEmailAndPassword(email, password, new FirebaseAuthentication.AddOnSignUpComplete() {
            @Override
            public void onSignUpComplete(Boolean isSuccess) {
                Fragment fragment;
                if(isSuccess){
                    FirebaseUser user = FirebaseAuthentication.getInstance().getCurrentUser();
                    User userModel = new User(username,email, "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png",user.getUid());
                    UserService.getInstance().createUser(userModel);
                    fragment = new Profife_notLogin_Fragment();
                    loadFragment(R.id.frame_container, fragment);
                } else {
                    fragment = new SignUpFragment();
                    loadFragment(R.id.frame_container, fragment);
                }
            }

        });
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(R.id.frame_container, fragment);
                    return true;
                case R.id.navigation_star:
                    fragment = new ConvenientFragment();
                    loadFragment(R.id.frame_container, fragment);
                    return true;
                case R.id.navigation_place:
                    fragment = new MapFragment();
                    loadFragment(R.id.frame_container, fragment);
                    return true;
                case R.id.navigation_user:
                    fragment = new Profife_notLogin_Fragment();
                    loadFragment(R.id.frame_container, fragment);

                    return true;
            }
            return false;
        }
    };

    //@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView = (SearchView) item.getActionView();
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void loadFragment(int id,Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
