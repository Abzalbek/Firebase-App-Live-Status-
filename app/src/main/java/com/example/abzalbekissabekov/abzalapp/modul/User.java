package com.example.abzalbekissabekov.abzalapp.modul;

/**
 * Created by abzalbekissabekov on 1/26/18.
 */

public class User {

    private String displayNmae;
    private String email;
    private String photoUrl;
    private String userId;

    public User() {
    }

    public User(String displayNmae, String email, String photoUrl, String userId) {
        this.displayNmae = displayNmae;
        this.email = email;
        this.photoUrl = photoUrl;
        this.userId = userId;
    }

    public String getDisplayNmae() {
        return displayNmae;
    }

    public void setDisplayNmae(String displayNmae) {
        this.displayNmae = displayNmae;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
