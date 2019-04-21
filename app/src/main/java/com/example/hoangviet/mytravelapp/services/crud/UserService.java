
package com.example.hoangviet.mytravelapp.services.crud;

import android.app.Service;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.hoangviet.mytravelapp.Models.User;
import com.example.hoangviet.mytravelapp.services.FirestoreService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserService extends FirestoreService {

    private static String TAG = "UserService";

    private static UserService instance = null;

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public interface FirebaseCallback{
        void onGetUserData(User user);
    }

    public void readData(String uid, final FirebaseCallback firebaseCallback){

        final User userData = new User("","","","");

        mFirestore.collection("users")
                .document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    userData.setAvatar(documentSnapshot.getString("avatar"));
                    userData.setUsername(documentSnapshot.getString("username"));
                    firebaseCallback.onGetUserData(userData);
                } else {
                    firebaseCallback.onGetUserData(userData);
                }
            }
        });

    }
    public void createUser(User user){
        Map<String, Object> map = new HashMap<>();
        map.put("address","Dak Lak");
        map.put("username",user.getUsername());
        map.put("avatar", user.getAvatar());
        map.put("email", user.getEmail());
        mFirestore.collection("users")
                .document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"write.complete");
                        } else {
                            Log.d(TAG,"write.complete:failure", task.getException());
                        }
                    }
                });
    }

}