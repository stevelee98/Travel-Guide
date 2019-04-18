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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangviet.mytravelapp.helper.BottomNavigationBehavior;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        ConvenientFragment.OnFragmentInteractionListener,
        Profife_notLogin_Fragment.OnFragmentInteractionListener,
        SigninFragment.OnFragmentInteractionListener ,
        SignUpFragment.OnFragmentInteractionListener,
        SelectLanguageDialog.OnFragmentInteractionListener
{

    //private Toolbar toolbar;
    //public TextView textView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String currentLanguage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        currentLanguage = "en_US";
        mAuth = FirebaseAuth.getInstance();


        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //textView = (TextView) toolbar.findViewById(R.id.txt_cityname);
        //textView.setText("Home");
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public FirebaseUser getCurrentUser(){
        try {
            this.user = mAuth.getCurrentUser();

            Toast.makeText(this,
                    this.user.getEmail()
                    , Toast.LENGTH_SHORT).show();

            return this.user;
        } catch(Exception err){
            Log.w("ERROR", "signInWithEmail:failure", err);
            throw err;
        }

    }
    public boolean signOut(){
        try {
            mAuth.signOut();
            return true;
        } catch (Exception err){
            Log.w("ERROR", "signOut:failure", err);
            return false;
        }
    }
    public void onSelectLanguageFinish(String language){
        this.currentLanguage = language;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void signIn(String email, String password){
        Toast.makeText(this,
                password
                , Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Fragment fragment;
                        if(task.isSuccessful()){

                            fragment = new Profife_notLogin_Fragment();
                            loadFragment(R.id.frame_container, fragment);

                        } else {
                            Log.w("ERROR", "signInWithEmail:failure", task.getException());
                            fragment = new SigninFragment();
                            loadFragment(R.id.frame_container, fragment);
                        }
                    }
                });
    }

    public void createAccount(String email, String password) {
        Toast.makeText(this,
                "Dang nhap"
                , Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Fragment fragment;
                        if(task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();
                            fragment = new HomeFragment();
                            loadFragment(R.id.frame_container, fragment);
                        } else {
                            fragment = new HomeFragment();
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
                    Toast.makeText(MainActivity.this, "Place", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_user:
                    fragment = new Profife_notLogin_Fragment();
                    loadFragment(R.id.frame_container, fragment);

                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void loadFragment(int id,Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
