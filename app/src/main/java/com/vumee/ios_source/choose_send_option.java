package com.vumee.ios_source;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class choose_send_option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_send_option);
        getrepid();

        final Button homebutton = this.findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), MainActivity.class);
                choose_send_option.this.startActivity(intent);
            }
        });

        final Button backbutton = this.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), MainActivity.class);
                choose_send_option.this.startActivity(intent);
            }
        });

        ImageView homebackround = findViewById(R.id.homebacround);
        homebackround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), MainActivity.class);
                choose_send_option.this.startActivity(intent);
            }
        });

        ImageView backbackground = findViewById(R.id.backbackground);
        backbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), MainActivity.class);
                choose_send_option.this.startActivity(intent);

            }
        });

        final Button enterbutton = this.findViewById(R.id.newnumbtn);
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), type_number_in.class);
                choose_send_option.this.startActivity(intent);
            }
        });

        final Button choosebutton = this.findViewById(R.id.choosebtn);
        choosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(choose_send_option.this.getApplicationContext(), pick_from_contacts.class);
                choose_send_option.this.startActivity(intent);
            }
        });



    }

//retrieves relevant repID from firebase and saves it in global class variable Repnumber to be attached to the UTM while sending message
    private void getrepid() {
        FirebaseAuth mAuth;
        DatabaseReference myRef;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        if (userID != null) {
            //FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = FirebaseDatabase.getInstance().getReference().child("repid").child(userID);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Repnumber = String.valueOf(dataSnapshot.child("id").getValue());
                    GlobalClass globalClass = (GlobalClass) choose_send_option.this.getApplicationContext();
                    globalClass.setRepID(Repnumber);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Write your code here
        final Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        this.startActivity(intent);
    }
}
