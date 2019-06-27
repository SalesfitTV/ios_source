package com.vumee.ios_source;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    // UI references.
    private EditText mEmail, mPassword;
    private Button btnSignIn;
    // --Commented out by Inspection (2019-06-26 09:59):private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declare buttons and edit texts in oncreate
        mEmail = findViewById(R.id.emailField);
        mPassword = findViewById(R.id.passwordField);
        btnSignIn = findViewById(R.id.loginBtn);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();

                if(!email.equals("") && !pass.equals("")){

                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (!task.isSuccessful())
                                Toast.makeText(login.this, "Logging you in", Toast.LENGTH_SHORT).show();
                            adddevicetodb();

                            {
                                try
                                {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthInvalidUserException invalidEmail)
                                {
                                    Log.d(TAG, "onComplete: invalid_email");
                                    Toast.makeText(login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                    // TODO: take your actions!
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                                {
                                    Log.d(TAG, "onComplete: wrong_password");
                                    Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                    // TODO: Take your action
                                }
                                catch (Exception e)
                                {
                                    Log.d(TAG, "onComplete: " + e.getMessage());
                                    Toast.makeText(login.this, "Logging you in", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    );
                }else{
                    toastMessage("Please enter a valid login");
                }
            }
        });

    }

    private void adddevicetodb() {
        /*
         * Code you want to run on the thread goes here
         */
        this.mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.myRef = mFirebaseDatabase.getReference();
        // Read from the database
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                final FirebaseUser user = mAuth.getCurrentUser();
                final String userID = user.getUid();
                String deviceAppUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                if (deviceAppUID!=null) {
                    myRef.child("loginactivity").child(userID).child(deviceAppUID).setValue("true");
                }


            }

            @Override
            public void onCancelled(final DatabaseError error) {
                // Failed to read value

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //add a toast to show when successfully signed in
    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onBackPressed() {
    }
}
