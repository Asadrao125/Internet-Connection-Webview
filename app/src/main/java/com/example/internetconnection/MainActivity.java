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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button buttonOne, buttonTwo;
    CoordinatorLayout coordinatorLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);

        coordinatorLayout = this.<CoordinatorLayout>findViewById(R.id.coordinatorLayout);
        if (isConnected(getApplicationContext()))
        {
            buttonOne.setVisibility(View.VISIBLE);
        }
        else
        {
            buttonTwo.setVisibility(View.VISIBLE);
        }

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Internet Connected!", Toast.LENGTH_SHORT).show();
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "No Internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
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