package com.w8india.w8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {



    Button connectwithdriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        connectwithdriver=findViewById(R.id.connectwithdriver);
        connectwithdriver.setOnClickListener(v -> Selection());



    }
    public void Selection(){
        Intent intent = new Intent(this, Selection.class);
        startActivity(intent);
    }

}