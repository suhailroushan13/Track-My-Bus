package com.w8india.w8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Friend extends AppCompatActivity {

    Button share;
    RatingBar ratingBar;
    Button rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ratingBar = findViewById(R.id.ratingBar);
        rate = findViewById(R.id.rate);


        share=findViewById(R.id.sharebtn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "W8");
                    String shareMessgae = "  Track My Bus is an Application which Track the Real Time Location Of College Buses https://play.google.com/store/apps/details?" + BuildConfig.APPLICATION_ID + "\n\n Learn More :  https://trackmybus.tech/ ";

                    intent.putExtra(Intent.EXTRA_TEXT, shareMessgae);
                    startActivity(Intent.createChooser(intent, "SHARE"));
                } catch (Exception e) {

                    Toast.makeText(Friend.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
