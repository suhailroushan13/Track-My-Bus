package com.w8india.w8;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Home extends AppCompatActivity {
    private DrawerLayout drawer;


    private Button mLogOutBtn;
    private FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Student_Number.class));
        }
        mLogOutBtn = findViewById(R.id.log_out_btn);

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Home.this , Student_OTP.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(Home.this , Student_OTP.class));
            finish();
        }
    }
}