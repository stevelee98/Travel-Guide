package com.example.hoangviet.mytravelapp.auth;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.hoangviet.mytravelapp.Profife_notLogin_Fragment;
import com.example.hoangviet.mytravelapp.R;
import com.example.hoangviet.mytravelapp.SigninFragment;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseAuthentication {
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser() != null){

            }
        }
    };

    private GoogleSignInClient mGoogleSignInClient;

    private static FirebaseAuthentication instance = null;

    private FirebaseUser user = null;


    public static FirebaseAuthentication getInstance() {
        if(instance == null) {
            instance = new FirebaseAuthentication();
        }
        return instance;
    }

    public interface AddOnSignInComplete {
        void onSignInComplete(Boolean isSuccess);

    }
    public interface AddOnSignUpComplete {
        void onSignUpComplete(Boolean isSuccess);

    }

    public FirebaseUser getCurrentUser(){
        if(user == null){
            user = mAuth.getCurrentUser();
        }
        return  user;
    }

    public boolean signOut(){
        try {

            mAuth.signOut();

            return true;
        } catch (Exception err){
            return false;
        }
    }


    public void SignInWithEmailAndPassword(String email, String password, final  AddOnSignInComplete callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            callback.onSignInComplete(true);
                        } else {
                            callback.onSignInComplete(false);
                        }
                    }
                });
    }
    public void SignUpWithEmailAndPassword(String email, String password, final AddOnSignUpComplete callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            callback.onSignUpComplete(true);
                        } else {
                            callback.onSignUpComplete(false);
                        }
                    }
                });
    }

    public interface AddOnSignInByFacebookComplete {
        void onSignUpComplete(Boolean isSuccess);

    }

    public void handleFacebookAccessToken(AccessToken token, final  AddOnSignInByFacebookComplete callback) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            callback.onSignUpComplete(true);
                        } else {
                            callback.onSignUpComplete(false);
                        }
                    }
                });
    }
    public interface AddOnSignInByGoogleComplete {
        void onSignUpComplete(Boolean isSuccess, GoogleSignInAccount account);
        void onSignUpError();
    }
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask, final AddOnSignInByGoogleComplete callback) {
        //final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        final GoogleSignInAccount account = completedTask.getResult();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onSignUpComplete(true, account);
                        } else {
                            // If sign in fails, display a message to the user
                            callback.onSignUpError();
                        }

                    }
                });

    }


}
