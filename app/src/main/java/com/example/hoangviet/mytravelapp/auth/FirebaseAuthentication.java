package com.example.hoangviet.mytravelapp.auth;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.hoangviet.mytravelapp.Profife_notLogin_Fragment;
import com.example.hoangviet.mytravelapp.R;
import com.example.hoangviet.mytravelapp.SigninFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseAuthentication {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
}
