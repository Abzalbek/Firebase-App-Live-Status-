package com.example.abzalbekissabekov.abzalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mUserImageView;
    private EditText mUserNameEditText;
    private Button mUserUpdateButton;

    private String passedUserID;
    private DatabaseReference mUserDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //assign variables

        mUserImageView = findViewById(R.id.userImageViewProfile);
        mUserNameEditText = findViewById(R.id.userNameEditTextProfile);
        mUserUpdateButton = findViewById(R.id.updateButtonProfile);

        passedUserID = getIntent().getStringExtra("USER_ID");
        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDB.child(passedUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("displayName").getValue(String.class);
                String photoUrl = dataSnapshot.child("photoUrl").getValue(String.class);

                mUserNameEditText.setText(name);
                try {
                    Picasso.with(ProfileActivity.this).load(photoUrl).placeholder(R.mipmap.ic_launcher).into(mUserImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
