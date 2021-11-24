package com.w8india.w8;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
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
        user = auth.getCurrentUser();
        busmain.animate().translationX(25).translationY(25).start();

//        Timer timer = new Timer();
//        int begin = 0;
//        int timeInterval = 500;
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                //call the method
//
//            }
//        }, begin, timeInterval);







    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

//    /*public void Selection(){
//        Intent intent = new Intent(this, Selection.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }**/


    @Override
    protected void onStart() {
        super.onStart();
        if (isNetworkConnected()){


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
                            if (user== null) {
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
}