package com.example.hoangviet.mytravelapp.Models;

public  class User {
    private String username;
    private String email;
    private String avatar;
    private String uid;

    public User(String username, String email, String avatar, String uid){
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.uid = uid;
    }
    public String getUsername(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public String getUid(){
        return this.uid;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

}
