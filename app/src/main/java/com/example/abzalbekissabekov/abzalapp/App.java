package com.example.abzalbekissabekov.abzalapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by abzalbekissabekov on 1/26/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
