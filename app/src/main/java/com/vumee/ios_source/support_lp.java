package com.vumee.ios_source;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

public class support_lp extends AppCompatActivity {


    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_lp);
        this.webView = this.findViewById(R.id.supportwebView);

        this.webView.setWebViewClient(new MyWebViewClient());
        final WebSettings browserSetting = this.webView.getSettings();
        browserSetting.setJavaScriptEnabled(true);
        this.loadURL();
        final Button homebutton = this.findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(support_lp.this.getApplicationContext(), MainActivity.class);
                support_lp.this.startActivity(intent);
            }
        });

        final Button backbutton = this.findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final WebView browser = support_lp.this.findViewById(R.id.supportwebView);
                if (browser.canGoBack()) {
                    browser.goBack();
                } else {

                    final Intent intent = new Intent(support_lp.this.getApplicationContext(), MainActivity.class);
                    support_lp.this.startActivity(intent);
                }

            }
        });

        ImageView homebackround = findViewById(R.id.homebacround);
        homebackround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(support_lp.this.getApplicationContext(), MainActivity.class);
                support_lp.this.startActivity(intent);
            }
        });

        ImageView backbackground = findViewById(R.id.backbackground);
        backbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WebView browser = support_lp.this.findViewById(R.id.supportwebView);
                if (browser.canGoBack()) {
                    browser.goBack();
                } else {
                    final Intent intent = new Intent(support_lp.this.getApplicationContext(), MainActivity.class);
                    support_lp.this.startActivity(intent);
                }
            }
        });

    }


    private void loadURL() {
        this.webView.loadUrl("https://sftv.app/app-gallery/share");
    }

    private class MyWebViewClient extends WebViewClient {




        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (url.startsWith("http://open/smsactivity/")) {
                final Intent intent1 = new Intent(support_lp.this.getApplicationContext(), choose_send_option.class);
                support_lp.this.startActivity(intent1);
            }
        }



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public  boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request){

            final String clicked_url = String.valueOf(request.getUrl());

            if (clicked_url.startsWith("http://open/smsactivity/")) {


                final GlobalClass globalClass = (GlobalClass) support_lp.this.getApplicationContext();
                globalClass.setUrlcolleccted(clicked_url);
                final Intent intent1 = new Intent(support_lp.this.getApplicationContext(), choose_send_option.class);
                support_lp.this.startActivity(intent1);
                view.loadUrl("https://sftv.app/app-gallery/share");

            }
            return false;
        }



        @Override public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {

            final String url = view.getUrl();
            if(url.startsWith("https://sftv.app")){
                view.loadUrl("file:///android_asset/error.html");
            }
        }
    }


    @Override
    public void onBackPressed() {
        final WebView browser = this.findViewById(R.id.supportwebView);
        if (browser.canGoBack()) {
            browser.goBack();
        } else {

            final Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
            this.startActivity(intent);
        }
    }
}
