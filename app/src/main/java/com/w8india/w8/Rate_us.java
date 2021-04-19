package com.w8india.w8;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

public class Rate_us extends AppCompatActivity {


    RatingBar ratingBar;
    Button rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
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


        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, int i) {

                Toast.makeText(Rate_us.this, " baseRatingBar.getRating()", Toast.LENGTH_SHORT).show();
                if (baseRatingBar.getRating() < 3) {
                    Toast.makeText(Rate_us.this, "WHy low rating?", Toast.LENGTH_SHORT).show();
                } else if (baseRatingBar.getRating() > 3) {
                    Toast.makeText(Rate_us.this, "Thanks", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }


        });

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



