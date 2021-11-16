package com.w8india.w8;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hsalf.smileyrating.SmileyRating;

public class Support_us extends AppCompatActivity {


    private static final String TAG = "t";
    RatingBar ratingBar, mratingbar;
    Button rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_us);
        rate = findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Support_us.this, "Please Give Us a Review", Toast.LENGTH_LONG).show();
            }
        });
        SmileyRating smileyRating=findViewById(R.id.smile_rating);
        SmileyRating.Type smiley = smileyRating.getSelectedSmiley();
// You can compare it with rating Type
        smileyRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                // You can compare it with rating Type
                if (SmileyRating.Type.GREAT == type) {
                    Log.i(TAG, "Wow, the user gave high rating");
                }
                // You can get the user rating too
                // rating will between 1 to 5
                int rating = type.getRating();
                if(rating<=3){
                    Toast.makeText(Support_us.this, "Please provide a feedback", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Support_us.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                }else if(rating>=4){
                    Toast.makeText(Support_us.this, "You're Too Good ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }


            });
        }
        // You can get the user rating too
        // rating will between 1 to 5, but -1 is none selected


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
                    Toast.makeText(Support_us.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
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



