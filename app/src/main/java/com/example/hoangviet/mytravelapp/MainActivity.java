package com.example.hoangviet.mytravelapp;

import android.annotation.SuppressLint;
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
import com.example.hoangviet.mytravelapp.auth.FirebaseAuthentication;
import com.example.hoangviet.mytravelapp.helper.BottomNavigationBehavior;
import com.example.hoangviet.mytravelapp.services.crud.UserService;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        ConvenientFragment.OnFragmentInteractionListener,
        Profife_notLogin_Fragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener ,
        SignUpFragment.OnFragmentInteractionListener,
        EmergencyFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        SearchView.OnQueryTextListener,
        SelectLanguageDialog.OnFragmentInteractionListener{



    private static final String TAG = "MainActivity";



    //public SearchView searchView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }





    public FirebaseUser getCurrentUser(){
        return FirebaseAuthentication.getInstance().getCurrentUser();
    }

    public boolean signOut(){
        return FirebaseAuthentication.getInstance().signOut();
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

    @Override
    public void onStart() {
        super.onStart();
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

    public void createAccount(final String email, String password, final String username) {
        Toast.makeText(this,
                "Dang "
                , Toast.LENGTH_SHORT).show();
        FirebaseAuthentication.getInstance().SignUpWithEmailAndPassword(email, password, new FirebaseAuthentication.AddOnSignUpComplete() {
            @Override
            public void onSignUpComplete(Boolean isSuccess) {
                Fragment fragment;
                if(isSuccess){
                    FirebaseUser user = FirebaseAuthentication.getInstance().getCurrentUser();
                    User userModel = new User(username,email, "",user.getUid());
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
