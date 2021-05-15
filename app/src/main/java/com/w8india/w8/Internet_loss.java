package com.w8india.w8;

import android.content.Context;
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

import java.io.IOException;

import static com.w8india.w8.Constants.isOnline;

public class Internet_loss extends AppCompatActivity {

    Button checkInternet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_loss);

        checkInternet = findViewById(R.id.try_again);
        checkInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(isOnline()){
                        finish();
                    }else{
                        Toast.makeText(Internet_loss.this, "Unable to connect! Check connection and try again", Toast.LENGTH_LONG).show();
                    }

            }
        });

    }

}

