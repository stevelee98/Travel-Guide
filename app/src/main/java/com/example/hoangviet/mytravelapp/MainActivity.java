package com.example.hoangviet.mytravelapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangviet.mytravelapp.helper.BottomNavigationBehavior;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        ConvenientFragment.OnFragmentInteractionListener,
        Profife_notLogin_Fragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener ,
        SignUpFragment.OnFragmentInteractionListener,
        EmergencyFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        SearchView.OnQueryTextListener{

    //private Toolbar toolbar;
    //public TextView textView;


    //public SearchView searchView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //textView = (TextView) toolbar.findViewById(R.id.txt_cityname);
        //textView.setText("Home");
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

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
