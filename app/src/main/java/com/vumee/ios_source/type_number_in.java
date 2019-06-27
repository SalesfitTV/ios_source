package com.vumee.ios_source;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class type_number_in extends AppCompatActivity {
    private EditText txtMobile;
    private EditText venueName;
    private EditText customerName;

    private Button mAddToDB;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String textmessagefinal = "";

    private static final String TAG = "AddToDatabase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_number_in);

        final Button homebutton = this.findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(type_number_in.this.getApplicationContext(), MainActivity.class);
                type_number_in.this.startActivity(intent);
            }
        });

        final Button backbutton = this.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(type_number_in.this.getApplicationContext(), choose_send_option.class);
                type_number_in.this.startActivity(intent);
            }
        });

        ImageView homebackround = findViewById(R.id.homebacround);
        homebackround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(type_number_in.this.getApplicationContext(), MainActivity.class);
                type_number_in.this.startActivity(intent);
            }
        });

        ImageView backbackground = findViewById(R.id.backbackground);
        backbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(type_number_in.this.getApplicationContext(), MainActivity.class);
                type_number_in.this.startActivity(intent);

            }
        });

        this.txtMobile = this.findViewById(R.id.mblTxt);
        this.venueName = this.findViewById(R.id.venuefield);
        this.customerName = this.findViewById(R.id.custname);
        final GlobalClass globalClass = (GlobalClass) this.getApplicationContext();
        final String finalurl = globalClass.getUrlcolleccted();
        final String repid = globalClass.getRepID();
        Button btnSms = this.findViewById(R.id.btnSend);
        /*
         * Code you want to run on the thread goes here
         */
        this.mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.myRef = mFirebaseDatabase.getReference();
        this.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(type_number_in.TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(type_number_in.TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };
        // Read from the database

// save data to fire base and send sms
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String mobnum = type_number_in.this.txtMobile.getText().toString();
                final String finalurl1 = finalurl.substring(24);
                final EditText txtMessage1 = type_number_in.this.findViewById(R.id.msgTxt);
                final String copyfinal = txtMessage1.getText().toString();
                final GlobalClass globalClass = (GlobalClass) type_number_in.this.getApplicationContext();
                String reptag = globalClass.getRepID();
                final String finalurlfordb = globalClass.getUrlcolleccted();
                textmessagefinal = (copyfinal+"\n"+finalurl1+"?utm_source="+mobnum+reptag);
                Log.d(type_number_in.TAG, "onClick: Attempting to add object to database.");
                final Date currentTime = Calendar.getInstance().getTime();
                final String datetime = currentTime.toString();
                final String VENUENAME = type_number_in.this.venueName.getText().toString();
                final String mobile = type_number_in.this.txtMobile.getText().toString();
                final String customername = type_number_in.this.customerName.getText().toString();
                final FirebaseUser userin = type_number_in.this.mAuth.getCurrentUser();
                final String userinfo = userin.getUid();
                final String urlcollected = finalurlfordb.substring(41);
                final String mob = urlcollected.replaceAll("/", ":")+":"+"sn";
                final String namemob;
                namemob = userinfo+";"+datetime+";"+VENUENAME+";"+mobile+";"+customername+";"+mob;
                final FirebaseUser user = type_number_in.this.mAuth.getCurrentUser();
                final String userID = user.getUid();
                type_number_in.this.myRef.child("useractivity").child(userID).child(namemob).setValue("true");
                if(!mobile.equals("") && !VENUENAME.equals("") && !customername.equals("")){
                    try{
                        final String SENT = "SMS_SENT";
                        final PendingIntent sentPI = PendingIntent.getBroadcast(type_number_in.this, 0, new Intent(SENT), 0);
                        // ---when the SMS has been sent---
                        registerReceiver(
                                new BroadcastReceiver()
                                {
                                    @Override
                                    public void onReceive(final Context arg0, final Intent arg1)
                                    {
                                        switch(this.getResultCode())
                                        {
                                            case Activity.RESULT_OK:
                                                Toast.makeText(type_number_in.this.getBaseContext(), "SMS sent successfully", Toast.LENGTH_LONG).show();
                                                final Intent intent2 = new Intent(type_number_in.this.getApplicationContext(), save_to_contacts.class);
                                                type_number_in.this.startActivity(intent2);
                                                break;
                                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                                Toast.makeText(type_number_in.this.getBaseContext(), "SMS sent failed", Toast.LENGTH_LONG).show();
                                                break;
                                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                                Toast.makeText(type_number_in.this.getBaseContext(), "SMS sent failed: no service", Toast.LENGTH_LONG).show();
                                                break;
                                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                                Toast.makeText(type_number_in.this.getBaseContext(), "SMS sent failed", Toast.LENGTH_LONG).show();
                                                break;
                                            case SmsManager.RESULT_ERROR_RADIO_OFF:
                                                Toast.makeText(type_number_in.this.getBaseContext(), "SMS sent failed", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }
                                }, new IntentFilter(SENT));
                        final SmsManager smgr = SmsManager.getDefault();
                        ArrayList<String> parts = smgr.divideMessage(textmessagefinal);
                        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>(Collections.singleton(sentPI));
                        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
                        smgr.sendMultipartTextMessage(type_number_in.this.txtMobile.getText().toString(),null, parts,sentIntents,deliveryIntents);
                        globalClass.setCustname(customername);
                        globalClass.setCustmob(mobile);
                        globalClass.setVenuename(VENUENAME);
                    }
                    catch (final Exception e){
                        Toast.makeText(type_number_in.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(type_number_in.this, "Please ensure all fields are filled above", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
    @Override
    public void onStart() {
        super.onStart();
        this.mAuth.addAuthStateListener(this.mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (this.mAuthListener != null) {
            this.mAuth.removeAuthStateListener(this.mAuthListener);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Write your code here
        final Intent intent = new Intent(this.getApplicationContext(), choose_send_option.class);
        this.startActivity(intent);
    }
}
