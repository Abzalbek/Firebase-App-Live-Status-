package com.example.abzalbekissabekov.abzalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.abzalbekissabekov.abzalapp.modul.Status;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;
    private RecyclerView mHomeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Status");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mHomeRecycler = findViewById(R.id.homeRecylerView);
        mHomeRecycler.setHasFixedSize(true);
        mHomeRecycler.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            goToLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() == null) {
            goToLogin();
        }
    }

    private void goToLogin() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                mAuth.signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            case R.id.addNewMenu:
                startActivity(new Intent(HomeActivity.this, PostActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Status,StatusViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Status, StatusViewHolder>(
                Status.class,
                R.layout.status_row,
                StatusViewHolder.class,
                mStatusDB
        ) {
            @Override
            protected void populateViewHolder(StatusViewHolder viewHolder, Status model, int position) {
                viewHolder.setUserName(model.getUserId());
                viewHolder.setUserStatus(model.getUserStatus());
            }
        };

        mHomeRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {

        View view;

        public StatusViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setUserPhotoURL(Context context, String imageUrl) {
            ImageButton userImageButton = view.findViewById(R.id.userImageButton);
            Picasso.with(context).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(userImageButton);
        }

        public void setUserName(String name) {
            TextView userNameTextView = view.findViewById(R.id.userNameTextView);
            userNameTextView.setText(name);
        }

        public void setUserStatus(String status) {
            TextView userStatusTextView = view.findViewById(R.id.userStatusTextView);
            userStatusTextView.setText(status);
        }
    }

}
