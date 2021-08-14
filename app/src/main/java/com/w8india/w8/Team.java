package com.w8india.w8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import static com.w8india.w8.Constants.it;

public class Team extends AppCompatActivity implements View.OnClickListener{

TextView suhail,izhan;
ImageView izig,iztw,izli,srli;
String us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        suhail=findViewById(R.id.suhail);
        suhail.setOnClickListener(this);

        izhan=findViewById(R.id.izhan);
        izhan.setOnClickListener(this);
        srli=findViewById(R.id.lis);
        srli.setOnClickListener(this);
        izig=findViewById(R.id.igi);
        izig.setOnClickListener(this);
        iztw=findViewById(R.id.twi);
        iztw.setOnClickListener(this);
        izli=findViewById(R.id.lii);
        izli.setOnClickListener(this);







    }

    @Override
    public void onClick(View v) {
        String url;

       if(v==suhail){

           url="https://www.linkedin.com/in/suhail-roushan-bb8b85144/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==izhan){
           url="https://www.linkedin.com/in/mohdizhanali";
           it(Team.this,FirebaseAuth.getInstance());
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }else if(v==izig){
           url="https://www.instagram.com/izhan.this/";
           it(Team.this,FirebaseAuth.getInstance());
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }else if(v==iztw){
           url="https://twitter.com/_izhanAli";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }else if(v==izli){
           it(Team.this,FirebaseAuth.getInstance());
           url="https://www.linkedin.com/in/mohdizhanali";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }else if(v==srli){
           url="https://www.suhailroushan.com";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }


    }
}