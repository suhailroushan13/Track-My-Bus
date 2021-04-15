package com.w8india.w8;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Internet_loss extends AppCompatActivity {

    boolean isConnected = false;
    Button checkInternet;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_loss);

             checkInternet = findViewById(R.id.checkInternet);
             checkInternet.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if( isConnected){


                         Toast.makeText(Internet_loss.this, "Connected", Toast.LENGTH_SHORT).show();

                     }
                     else {

                         Intent i= new Intent(Internet_loss.this,notconnected.class);
                         startActivity(i);
                         finish();

                     }



                 }
             });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerNetworkCallback(){


        try {

            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    isConnected = false;
                }
            });




        }catch (Exception e){

            isConnected = false;

        }

    }

    private void unregisterNetworkCallback(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerNetworkCallback();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkCallback();
    }
}

