package com.w8india.w8;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedImageDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {



    //Button connectwithdriver;
    ImageView busmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /**connectwithdriver=findViewById(R.id.connectwithdriver);
        connectwithdriver.setOnClickListener(v -> Selection());**/

        busmain=findViewById(R.id.busmain);

        busmain.animate().translationX(25).translationY(25).start();
        Timer timer = new Timer();
        int begin = 0;
        int timeInterval = 500;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //call the method
                if (isConnected()){
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Your code
                            busmain.animate().cancel();
                            busmain.animate().translationX(450).setDuration(2000).start();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashScreen.this, Selection.class);
                                    startActivity(intent);

                                }
                            }, 2100);
                        }
                    });


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Your code
                            busmain.animate().cancel();
                        }
                    });

                }
            }
        }, begin, timeInterval);







    }
    /**public void Selection(){
        Intent intent = new Intent(this, Selection.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }**/
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}