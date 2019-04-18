package com.example.hoangviet.mytravelapp.data;

public class FirebaseUserData {
    private String name;
    private String email;
    private String photoUrl;
    private String uid;

    public FirebaseUserData(String name, String email, String photoUrl, String uid){
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.uid = uid;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public String getUid(){
        return this.uid;
    }
}
