package com.w8india.w8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Selection extends AppCompatActivity {
    Button slogin,dlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        slogin=findViewById(R.id.slogin);
        slogin.setOnClickListener(v -> Student_Number());

    }

    public void Student_Number(){
        Intent intent = new Intent(this, Student_Number.class);
        startActivity(intent);



    }
}