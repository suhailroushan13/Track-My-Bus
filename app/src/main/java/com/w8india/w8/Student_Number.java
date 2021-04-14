package com.w8india.w8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Student_Number extends AppCompatActivity {

    Button getotp, loginwithgoogle;
    EditText phone;
    TextInputEditText txtotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__number);

        getotp = findViewById(R.id.button);
        getotp.setOnClickListener(v -> Student_OTP());
        loginwithgoogle = findViewById(R.id.loginwithgoogle);
        loginwithgoogle.setOnClickListener(v -> Login());
        phone = findViewById(R.id.phone);


    }


    public void Student_OTP() {
        Intent intent = new Intent(this, Student_OTP.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void Login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }










    @Override
    public void onBackPressed()
    {
        showAlertDialog();

    }
    private void showAlertDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure ? You Want To Leave ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    }
