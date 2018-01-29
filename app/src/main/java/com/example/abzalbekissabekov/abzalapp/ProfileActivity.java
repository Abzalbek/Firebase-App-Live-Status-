package com.example.abzalbekissabekov.abzalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_PHOTO_CAPTURE = 1;
    private static final int REQUEST_PHOTO_PICK = 2;

    private ImageView mUserImageView;
    private EditText mUserNameEditText;
    private Button mUserUpdateButton;

    private String passedUserID;
    private DatabaseReference mUserDB;
    private FirebaseUser currentUser;
    private Uri mPhotoUri;
    private StorageReference mStorage;


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
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

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


        if (!passedUserID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            mUserImageView.setEnabled(false);
            mUserNameEditText.setFocusable(false);
            mUserUpdateButton.setVisibility(View.GONE);
        } else {
            // it is my profile
            mUserImageView.setEnabled(true);
            mUserNameEditText.setFocusable(true);
            mUserUpdateButton.setVisibility(View.VISIBLE);

        }


        // listen ot image view click
        mUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("How would you like to add your photo?");
//                builder.setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // take photo
//                        dispatchTakePhotoIntent();
//                    }
//                });
                builder.setNegativeButton("Choose photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //choose photo
                        dispatchChoosePhotoIntent();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        // listen to update button click
        mUserUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** update user name **/
                String newUserName = mUserNameEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(newUserName)) {
                    updateUserName(newUserName);
                }

                /**update user photo**/
                if(mPhotoUri!=null){
                    updateUserPhoto(mPhotoUri);
                }
            }
        });

        //Toast.makeText(getApplicationContext(), currentUser.getDisplayName(), Toast.LENGTH_LONG).show();

    }

    private void updateUserName(final String newUserName) {

        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build();
        currentUser.updateProfile(profileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Map<String, Object> updateUserNameNap = new HashMap<>();
                updateUserNameNap.put("displayNmae", newUserName);
                mUserDB.child(passedUserID).updateChildren(updateUserNameNap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Success of updating name!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateUserPhoto(Uri photoUri) {
        StorageReference userImageRef = mStorage.child("UserImages").child(currentUser.getUid()).child(photoUri.getLastPathSegment());
        userImageRef.putFile(photoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                @SuppressWarnings("VisibleForeTests") Uri uploadedImageUri = task.getResult().getDownloadUrl();

                Map<String, Object> updatePhotoMap = new HashMap<>();
                updatePhotoMap.put("photoUrl", uploadedImageUri.toString());
                mUserDB.child(currentUser.getUid()).updateChildren(updatePhotoMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Success of updating photo!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void dispatchTakePhotoIntent() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_PHOTO_CAPTURE);
        }
    }

    private void dispatchChoosePhotoIntent() {
        Intent choosePhoto = new Intent(Intent.ACTION_PICK);
        choosePhoto.setType("image/*");
        startActivityForResult(choosePhoto, REQUEST_PHOTO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CAPTURE && resultCode == RESULT_OK) {
            // success taking photo
            mPhotoUri = data.getData();
            mUserImageView.setImageURI(mPhotoUri);
        } else if (requestCode == REQUEST_PHOTO_PICK && resultCode == RESULT_OK) {
            // success picking photo
            mPhotoUri = data.getData();
            mUserImageView.setImageURI(mPhotoUri);
        }
    }
}
