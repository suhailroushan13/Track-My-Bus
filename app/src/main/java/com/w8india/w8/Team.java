package com.w8india.w8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Team extends AppCompatActivity {

TextView suhail,mannan,fawaz,izhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        suhail=findViewById(R.id.suhail);
        fawaz=findViewById(R.id.fawaz);
        mannan=findViewById(R.id.mannan);
        izhan=findViewById(R.id.izhan);

        suhail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/suhail-roushan-bb8b85144/"));
                startActivity(in);
            }
        });

        izhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mohdizhanali/"));
                startActivity(in);

            }
        });
        fawaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/syedfawazali/"));
                startActivity(in);

            }
        });

        mannan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mohd-abdul-mannan-88b7081a3/"));
                startActivity(in);

            }
        });




    }
}