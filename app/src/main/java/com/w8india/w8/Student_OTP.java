package com.w8india.w8;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.w8india.w8.Constants.isOnline;
import static com.w8india.w8.Constants.showSnack;

public class Student_OTP extends AppCompatActivity {


    Button resend;
    private Button mVerifyCodeBtn;
    private EditText otpEdit;
    private String OTP;
    private FirebaseAuth firebaseAuth;
    private static final int COUNTDOWN_STEP = 100;
    int TIME = 60500;
    CountDownTimer countDownTimer;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__o_t_p);
        mVerifyCodeBtn = findViewById(R.id.verifycode_btn);
        otpEdit = findViewById(R.id.verify_code_edit);
        no = getIntent().getStringExtra("no");
       resend=findViewById(R.id.resend);


        firebaseAuth = FirebaseAuth.getInstance();
        resend.setEnabled(false);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline(Student_OTP.this)) {
                    startTimer(1);
                    sendVerificationCode(no);
                    Toast.makeText(Student_OTP.this, "OTP Sent Successfully!", Toast.LENGTH_SHORT).show();
                    resend.setEnabled(false);
                } else {
                    startActivity(new Intent(Student_OTP.this, Internet_loss.class));

                }

            }
        });
        sendVerificationCode(no);
        startTimer(1);


        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVerifyCodeBtn.requestFocus();
                String code = otpEdit.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    otpEdit.setError("Enter valid code");
                    //otp.requestFocus();
                    return;
                }

                //verifying the code entered manually
                if (isOnline(Student_OTP.this)) {
                    verifyVerificationCode(code);
                } else {
                    startActivity(new Intent(Student_OTP.this, Internet_loss.class));
                }
            }
        });
    }
    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendToMain();
                }else{
                    Toast.makeText(Student_OTP.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser !=null){
            sendToMain();
        }
    }

    private void sendToMain(){
        startActivity(new Intent(Student_OTP.this , Home.class));
        finish();
    }
    private void startTimer(final int min) {
        countDownTimer = new CountDownTimer(60 * min * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                // format the textview to show the easily readable format
                resend.setText("Resend OTP (" + String.format("%02d", seconds % 60) + "s)");

            }

            @Override
            public void onFinish() {
                resend.setEnabled(true);
                //if(resend.getText().equals("Resend OTP (00s)")){
                resend.setText("Resend OTP");
                resend.setEnabled(true);


            }
        }.start();
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otpEdit.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(Student_OTP.this, "Verification Failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("VERIFICATION", e.getStackTrace().toString()+e.getMessage());
        }



        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void sendVerificationCode(String no) {
        /**honeAuthProvider.getInstance().verifyPhoneNumber(
                no,
                60,
                TimeUnit.SECONDS,
                (Activity) TaskExecutors.MAIN_THREAD,
                mCallbacks);**/
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(no)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(Student_OTP.this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyVerificationCode(String code) {
        //creating the credential


        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Student_OTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            setResult(1);
                            finish();
                            startActivity(new Intent(Student_OTP.this, Select_Bus.class));


                        } else {
                            /**progressBar.setVisibility(View.INVISIBLE);
                            textInputLayout.setVisibility(View.VISIBLE);
                            resend.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            login.setVisibility(View.VISIBLE);**/
                            //verification unsuccessful.. display an error message


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Student_OTP.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Student_OTP.this, "Unable to verify please retry later", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        setResult(1);
        super.onDestroy();
    }
}






































