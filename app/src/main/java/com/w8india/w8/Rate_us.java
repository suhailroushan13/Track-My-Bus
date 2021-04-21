package com.w8india.w8;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.BaseRatingBar.OnRatingChangeListener;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.UUID;

public class Rate_us extends AppCompatActivity {


    RatingBar ratingBar, mratingbar;
    Button rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);


        ratingBar = findViewById(R.id.ratingBar);
        mratingbar = findViewById(R.id.idratingBar);
        mratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating<=3){
                    Toast.makeText(Rate_us.this, "Please provide a feedback", Toast.LENGTH_SHORT).show();
                    try {

                        String uriText =
                                "mailto:w8india@gmail.com" +
                                        "?subject=" + Uri.encode("Feedback for App") +
                                        "&body=" + Uri.encode("Hi Developer's");
                        Uri uri = Uri.parse(uriText);
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(uri);
                        startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Rate_us.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                }else if(rating>=4){
                    Toast.makeText(Rate_us.this, "You're Too Good ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        rate = findViewById(R.id.rate);
        rate = findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });
        ScaleRatingBar ratingBar = new ScaleRatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setStarPadding(10);


        ratingBar.setClickable(true);


        ratingBar.setOnRatingChangeListener(new OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, int i) {

                if (baseRatingBar.getRating() < 3) {
                    Toast.makeText(Rate_us.this, "WHy low rating?", Toast.LENGTH_SHORT).show();
                } else if (baseRatingBar.getRating() > 3) {
                    Toast.makeText(Rate_us.this, "Thanks", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }


        });
        ScaleRatingBar scaleRatingBar = new ScaleRatingBar(Rate_us.this);
        ratingBar.setNumStars(5);
       
        ratingBar.setRating(3);
        ratingBar.setStarPadding(10);
      
        ratingBar.setClickable(true);
       
        ratingBar.setEmptyDrawableRes(R.drawable.empty);
        ratingBar.setFilledDrawableRes(R.drawable.filled);
        ratingBar.setOnRatingChangeListener(new OnRatingChangeListener() {


            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, int i) {
                
            }

            
            public void onRatingChange(BaseRatingBar ratingBar, int rating, boolean fromUser) {

            }
        });


    }
    public class AnimationRatingBar extends BaseRatingBar {

        protected Handler mHandler;
        protected Runnable mRunnable;
        protected String mRunnableToken = UUID.randomUUID().toString();

        protected AnimationRatingBar(Context context) {
            super(context);
            init();
        }

        protected AnimationRatingBar(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        protected AnimationRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            mHandler = new Handler();
        }

        protected void postRunnable(Runnable runnable, long ANIMATION_DELAY) {
            if (mHandler == null) {
                mHandler = new Handler();
            }

            long timeMillis = SystemClock.uptimeMillis() + ANIMATION_DELAY;
            mHandler.postAtTime(runnable, mRunnableToken, timeMillis);
        }

    }
    @Override
    public void onBackPressed() {
        showAlertDialog();

    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Feedback");
        builder.setMessage("Wana Change Your Mind ? Give us a Feedback");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    String uriText =
                            "mailto:w8india@gmail.com" +
                                    "?subject=" + Uri.encode("Feedback for App") +
                                    "&body=" + Uri.encode("Hi Developer's");
                    Uri uri = Uri.parse(uriText);
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(uri);
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Rate_us.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }



}



