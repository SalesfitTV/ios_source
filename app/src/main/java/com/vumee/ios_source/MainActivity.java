package com.vumee.ios_source;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
// --Commented out by Inspection START (2019-06-26 09:59):
//    // --Commented out by Inspect// --Commented out by Inspection (2019-06-26 09:59):ion (2019-06-26 09:59):private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
// --Commented out by Inspection STOP (2019-06-26 09:59)
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("pitchtest");




        final Button logout = this.findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseAuth.getInstance().signOut();
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),login.class);
                MainActivity.this.startActivity(intent);

            }
        });


        final Button offersbutton = this.findViewById(R.id.offersbutton);
        offersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),offers_lp.class);
                MainActivity.this.startActivity(intent);
            }
        });

        final Button categorybutton = this.findViewById(R.id.categorybutton);
        categorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),category_lp.class);
                MainActivity.this.startActivity(intent);
            }
        });
        final Button productsbutton = this.findViewById(R.id.productsbutton);
        productsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),products_lp.class);
                MainActivity.this.startActivity(intent);
            }
        });
        final Button supportbutton = this.findViewById(R.id.supportbutton);
        supportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),support_lp.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchBarActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton viewsbtn = this.findViewById(R.id.viewsbtn);
        viewsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this.getApplicationContext(),viewtally.class);
                MainActivity.this.startActivity(intent);
            }
        });
        //check for app permissions
        // in case of one or more permissions are not granted,
        // ActivityCompat.requestPermissions() will request permissions
        //and the control goes to onRequestPermissionsResult() callback method.

        final int Permission_All = 1;

        final String[] Permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SEND_SMS,
        };

        if(!MainActivity.hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }


    }




    public static boolean hasPermissions(final Context context, final String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(final String permission: permissions){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
    }
}
