package com.vumee.ios_source;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewtally extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtally);
        getrepid();
    }

    private void getrepid() {
        FirebaseAuth mAuth;
        DatabaseReference myRef;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        if (userID != null) {
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = FirebaseDatabase.getInstance().getReference().child("video_views").child(userID);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Repnumber = String.valueOf(dataSnapshot.child("lp_clicks").getValue());
                    TextView viewvalue = findViewById(R.id.viewsvalue);
                    viewvalue.setText("$"+Repnumber+".00");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
}
