package com.w8india.w8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Student_Number extends AppCompatActivity {

    Button getotp,loginwithgoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__number);

        getotp=findViewById(R.id.button);
        getotp.setOnClickListener(v -> Student_OTP());
        loginwithgoogle=findViewById(R.id.loginwithgoogle);
        loginwithgoogle.setOnClickListener(v -> Login());


    }

    public void Student_OTP(){
        Intent intent = new Intent(this, Student_OTP.class);
        startActivity(intent);

    }
    public void Login(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    }
