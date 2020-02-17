package com.example.internetconnection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    private WebView mWebview;
    private ImageView imageView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.no_Internet_Img);
        coordinatorLayout = this.<CoordinatorLayout>findViewById(R.id.coordinatorLayout);
        if (isConnected(getApplicationContext())) {
            //buttonOne.setVisibility(View.VISIBLE);
            mWebview = (WebView) findViewById(R.id.web_View);
            WebSettings webSettings = mWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            imageView.setVisibility(View.INVISIBLE);
            mWebview.loadUrl("http://www.test.abmtraders.com/");
            Log.i("Zzzzzz", "connected");
            mWebview.setWebViewClient(new WebViewClient());

        } else {
            //error msg
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceAsColor")
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting()) {
                final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Internet Connected!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).setActionTextColor(Color.WHITE);

                View snackView = snackbar.getView();

                snackView.setBackgroundColor(R.color.colorPrimary);

                snackbar.show();
                return true;

            } else {
                return false;
            }
        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(Color.WHITE);
            View snackView = snackbar.getView();
            snackView.setBackgroundColor(R.color.colorPrimary);
            snackbar.show();
            return false;
        }
    }
}