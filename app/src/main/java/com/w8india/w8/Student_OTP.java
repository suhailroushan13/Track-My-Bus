package com.w8india.w8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Student_OTP extends AppCompatActivity {

    Button getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__o_t_p);

        getotp=findViewById(R.id.getotp);
        getotp.setOnClickListener(v -> Select_Bus());


    }

    public void Select_Bus(){
        Intent intent = new Intent(this, Select_Bus.class);
        startActivity(intent);
    }
}