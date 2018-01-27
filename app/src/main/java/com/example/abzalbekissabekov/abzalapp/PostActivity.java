package com.example.abzalbekissabekov.abzalapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abzalbekissabekov.abzalapp.modul.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private EditText mStatusEditText;
    private Button mPostButton;


    private DatabaseReference mStatusDB;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //firebase
        mStatusDB = FirebaseDatabase.getInstance().getReference().child("Status");

        // assign variable

        mStatusEditText = findViewById(R.id.postEditText);
        mPostButton = findViewById(R.id.postButton);

        mDialog = new ProgressDialog(this);
        //listen to post post button click

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setMessage("Posting ...");
                mDialog.show();

                String status = mStatusEditText.getText().toString();

                if (TextUtils.isEmpty(status)) {
                    //error
                    mDialog.dismiss();
                } else {
                    //proceed
                    postStatusToFirebase(status, FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
        });
    }

    private void postStatusToFirebase(String userStatus, String userId) {

        Status status = new Status(userStatus, userId);

        mStatusDB.push().setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDialog.dismiss();
                mStatusEditText.setText(null);
                Toast.makeText(PostActivity.this, "Success!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
