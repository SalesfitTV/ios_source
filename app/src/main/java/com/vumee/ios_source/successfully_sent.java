package com.vumee.ios_source;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class successfully_sent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_sent);

        final Button menubutton = this.findViewById(R.id.menubtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(successfully_sent.this.getApplicationContext(), MainActivity.class);
                successfully_sent.this.startActivity(intent);
            }
        });

        final Button resend = this.findViewById(R.id.resendbtn);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(successfully_sent.this.getApplicationContext(), pick_from_contacts.class);
                successfully_sent.this.startActivity(intent);
            }
        });
    }
}
