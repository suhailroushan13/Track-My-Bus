package com.w8india.w8;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Student_Number extends AppCompatActivity {

    private Button mSendOTPBtn,loginwithgoogle;
    private TextView processText;
    private EditText countryCodeEdit , phoneNumberEdit;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;




    FirebaseUser user;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__number);

        user = FirebaseAuth.getInstance().getCurrentUser();


        mSendOTPBtn = findViewById(R.id.send_codebtn);
        phoneNumberEdit = findViewById(R.id.input_phone);
        loginwithgoogle=findViewById(R.id.loginwithgoogle);
        loginwithgoogle.setOnClickListener(v -> Login());





        mSendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneNumberEdit.getText().toString();
                String phoneNumber = "+91"+ phone;
                if(Constants.isOnline()) {
                    if (phone.isEmpty()) {
                        phoneNumberEdit.setError("Enter Your Number ");

                    } else if (phone.length() < 10) {
                        phoneNumberEdit.setError("Have You Forget Your Number ??");
                    } else {
                        Intent intent = new Intent(Student_Number.this, Student_OTP.class);
                        intent.putExtra("no", phoneNumber);

                        startActivityForResult(intent, 2);
                    }
                }else {
                    startActivity(new Intent(Student_Number.this, Internet_loss.class));
                }
            }
        });
        /**mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }



            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                processText.setText(e.getMessage());
                processText.setTextColor(Color.RED);
                processText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                //sometime the code is not detected automatically
                //so user has to manually enter the code
                processText.setText("OTP has been Sent");
                processText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent = new Intent(Student_Number.this , Student_OTP.class);
                        otpIntent.putExtra("auth" , s);
                        startActivity(otpIntent);
                    }
                }, 10000);

            }
        };**/
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user !=null) {
            sendToMain();
        }

    }
    private void sendToMain(){
        Intent mainIntent = new Intent(Student_Number.this , Home.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user !=null) {
            sendToMain();
        }
    }


    public void Login(){

        startActivity(new Intent(Student_Number.this, Login.class));


    }
}

