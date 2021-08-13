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

TextView suhail,mannan,fawaz,izhan;
ImageView srig, srtw, srli, izig,iztw,izli,faig,fatw,fali,maig,matw,mali;
FirebaseAuth auth;
String us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        auth = FirebaseAuth.getInstance();
        us = auth.getCurrentUser().getPhoneNumber();
        suhail=findViewById(R.id.suhail);
        suhail.setOnClickListener(this);
        fawaz=findViewById(R.id.fawaz);
        fawaz.setOnClickListener(this);
        mannan=findViewById(R.id.mannan);
        mannan.setOnClickListener(this);
        izhan=findViewById(R.id.izhan);
        izhan.setOnClickListener(this);
        srig=findViewById(R.id.igs);
        srig.setOnClickListener(this);
        srtw=findViewById(R.id.tws);
        srtw.setOnClickListener(this);
        srli=findViewById(R.id.lis);
        srli.setOnClickListener(this);
        izig=findViewById(R.id.igi);
        izig.setOnClickListener(this);
        iztw=findViewById(R.id.twi);
        iztw.setOnClickListener(this);
        izli=findViewById(R.id.lii);
        izli.setOnClickListener(this);

        faig=findViewById(R.id.igf);
        faig.setOnClickListener(this);
        fatw=findViewById(R.id.twf);
        fatw.setOnClickListener(this);
        fali=findViewById(R.id.lif);
        fali.setOnClickListener(this);
        maig=findViewById(R.id.igm);
        maig.setOnClickListener(this);
        matw=findViewById(R.id.twm);
        matw.setOnClickListener(this);
        mali=findViewById(R.id.lim);
        mali.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        String url;

       if(v==suhail){

           url="https://www.linkedin.com/in/suhail-roushan-bb8b85144/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==izhan){
           url="https://www.linkedin.com/in/mohdizhanali";
           it(Team.this,us);
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==fawaz){
           url="https://www.linkedin.com/in/syedfawazali/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==mannan){
           url="https://www.linkedin.com/in/mohd-abdul-mannan-88b7081a3/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

       }else if(v==srig){
           url="https://www.instagram.com/suhailroushan/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==izig){
           url="https://www.instagram.com/izhan.this/";
           it(Team.this,us);
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==faig){
           url="https://www.instagram.com/fawaz.exe/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==maig){
           url="https://www.instagram.com/_abdul_.mannan/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==srtw){
           url="https://twitter.com/suhailroushan13";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==iztw){
           url="https://twitter.com/_izhanAli";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==fatw){
           url="https://twitter.com/fawaz_exe";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==matw){
           Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
       }else if(v==srli){
           url="https://www.linkedin.com/in/suhail-roushan-bb8b85144/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==izli){
           it(Team.this,us);
           url="https://www.linkedin.com/in/mohdizhanali";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==fali){
           url="https://www.linkedin.com/in/syedfawazali/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }else if(v==mali){
           url="https://www.linkedin.com/in/mohd-abdul-mannan-88b7081a3/";
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
       }


    }
}