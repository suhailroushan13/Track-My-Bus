package com.w8india.w8;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity implements View.OnClickListener{
Button privacy,tnc,mail;
TextView version,contactusbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        privacy = findViewById(R.id.privacypolbtn);
        privacy.setOnClickListener(this);
        tnc = findViewById(R.id.termsbtn);
        tnc.setOnClickListener(this);
        mail = findViewById(R.id.contactusbtn);
        mail.setOnClickListener(this);
        version = findViewById(R.id.versionabt);


        version.setText("Version"+vname(this));



    }




    private String vname(Context context){
        try{
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Not Found";
        }
    }


    @Override
    public void onClick(View v) {
        if(v==mail){
            try {

                String uriText =
                        "mailto:w8india@gmail.com" +
                                "?subject=" + Uri.encode("Lords Bus App") +
                                "&body=" + Uri.encode("Hi Developer's");
                Uri uri = Uri.parse(uriText);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(uri);
                startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(About.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }else if(v==privacy){

            String url = "http://www.lordsbus.in/privacy.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            //TODO privacy policy
        }else if(v==tnc){

            String url = "http://www.lordsbus.in/terms.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            //TODO tnc
        }
    }
}
