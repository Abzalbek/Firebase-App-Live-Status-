package com.example.abzalbekissabekov.abzalapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abzalbekissabekov.abzalapp.modul.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignUpButton;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private DatabaseReference usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // assign variable
        mNameEditText = findViewById(R.id.nameEditTextRegister);
        mEmailEditText = findViewById(R.id.emailEditTextRegister);
        mPasswordEditText = findViewById(R.id.passwordEditTextRegister);
        mSignUpButton = findViewById(R.id.signUpButton);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");

        mDialog = new ProgressDialog(this);

        // listen to button click
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setMessage("Please wait...");
                mDialog.show();

                String name = mNameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    mDialog.dismiss();
                    // name can not be empty
                    showAlertDialog("Error!", "Name can not be empty");
                } else if (TextUtils.isEmpty(email)) {
                    mDialog.dismiss();
                    // email can not be empty
                    showAlertDialog("Error!", "Email can not be empty");
                } else if (TextUtils.isEmpty(password)) {
                    mDialog.dismiss();
                    //password can not be empty
                    showAlertDialog("Error!", "Password can not be empty");
                } else {
                    //proceed, all data is available
                    // sign up with firebase
                    registerUserToFirebase(email, password,name);
                }
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void registerUserToFirebase(String email, String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.dismiss();
                if (!task.isSuccessful()) {
                    // error registering user
                    showAlertDialog("Error!", task.getException().getMessage());
                } else {
                    //success
                    final FirebaseUser currentUser = task.getResult().getUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            User newUser = new User(currentUser.getDisplayName(), currentUser.getEmail(), "", currentUser.getUid());
                            usersDB.child(currentUser.getUid()).setValue(newUser);
                            // take user home
                            finish();
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                    });


                }
            }
        });
    }


}
