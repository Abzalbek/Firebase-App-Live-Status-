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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mStatusDB;
    private DatabaseReference mUserDB;
    private RecyclerView mHomeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Status");
        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");

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
            case R.id.myProfileMenu:
                Intent goToProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                goToProfile.putExtra("USER_ID",mAuth.getCurrentUser().getUid());
                startActivity(goToProfile);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Status, StatusViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Status, StatusViewHolder>(
                Status.class,
                R.layout.status_row,
                StatusViewHolder.class,
                mStatusDB
        ) {
            @Override
            protected void populateViewHolder(final StatusViewHolder viewHolder, final Status model, int position) {
                viewHolder.setUserStatus(model.getUserStatus());

                //query user with the model ID which is the user id
                mUserDB.child(model.getUserId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName = dataSnapshot.child("displayNmae").getValue(String.class);
                        String photoUrl = dataSnapshot.child("photoUrl").getValue(String.class);

                        viewHolder.setUserName(userName);

                        try {
                            viewHolder.setUserPhotoURL(getApplicationContext(), photoUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /**listen to image button click**/
                viewHolder.userImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // go to profile
                        Intent goToProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                        goToProfile.putExtra("USER_ID", model.getUserId());
                        startActivity(goToProfile);
                    }
                });

            }
        };

        mHomeRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ImageButton userImageButton;

        public StatusViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            userImageButton = view.findViewById(R.id.userImageButton);
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
