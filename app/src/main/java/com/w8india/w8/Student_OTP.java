package com.w8india.w8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;


public class Student_OTP extends AppCompatActivity implements View.OnClickListener {

        private PinView pinView;
        private Button next;
        private TextView topText,textU;
        private EditText userName, userPhone;
        private ConstraintLayout first, second;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        setContentView(R.layout.activity_student__o_t_p);


        pinView = findViewById(R.id.pinView);
        next = findViewById(R.id.button);
        next.setOnClickListener(v -> Select_Bus());





        }
        public void Select_Bus(){
            Intent intent = new Intent(this, Select_Bus.class);
            startActivity(intent);


        }

        @Override
        public void onClick(View v) {

        if (next.getText().equals("Let's go!")) {
        String name = userName.getText().toString();
        String phone = userPhone.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
        next.setText("Verify");
        first.setVisibility(View.GONE);
        second.setVisibility(View.VISIBLE);
        topText.setText("I Still don't trust you.nTell me something that only two of us know.");
        } else {
        Toast.makeText(Student_OTP.this, "Please enter the details", Toast.LENGTH_SHORT).show();
        }
        } else if (next.getText().equals("Verify")) {
        String OTP = pinView.getText().toString();
        if (OTP.equals("3456")) {
        pinView.setLineColor(Color.GREEN);
        textU.setText("OTP Verified");
        textU.setTextColor(Color.GREEN);
        next.setText("Next");
        } else {
        pinView.setLineColor(Color.RED);
        textU.setText("X Incorrect OTP");
        textU.setTextColor(Color.RED);
        }
        }



        }
        }





































