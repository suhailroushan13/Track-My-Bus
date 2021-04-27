package com.w8india.w8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


    FirebaseAuth auth;

    //Button connectwithdriver;
    ImageView busmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        /**connectwithdriver=findViewById(R.id.connectwithdriver);
//        connectwithdriver.setOnClickListener(v -> Selection());**/

        auth = FirebaseAuth.getInstance();
        busmain=findViewById(R.id.busmain);

        busmain.animate().translationX(25).translationY(25).start();
        Timer timer = new Timer();
        int begin = 0;
        int timeInterval = 500;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //call the method
                if (2<3){
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
                                    if (auth.getCurrentUser() == null) {
                                        finish();
                                        startActivity(new Intent(SplashScreen.this, Selection.class));
                                    }else{
                                        startActivity(new Intent(SplashScreen.this, Home.class));
                                        finish();

                                    }

                                }
                            }, 2000);
                        }
                    });


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Your code
                            busmain.animate().cancel();
                            Intent intent = new Intent(SplashScreen.this, Internet_loss.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        }, begin, timeInterval);







    }
//    /*public void Selection(){
//        Intent intent = new Intent(this, Selection.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }**/

}