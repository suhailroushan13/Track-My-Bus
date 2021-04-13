package com.w8india.w8;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class SplashScreen extends AppCompatActivity {



    Button connectwithdriver;
    ImageView busmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        connectwithdriver=findViewById(R.id.connectwithdriver);
        connectwithdriver.setOnClickListener(v -> Selection());

        busmain=findViewById(R.id.busmain);

        busmain.animate().translationX(450).setDuration(2000).start();





    }
    public void Selection(){
        Intent intent = new Intent(this, Selection.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}