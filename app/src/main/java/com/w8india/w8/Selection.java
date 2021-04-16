package com.w8india.w8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Selection extends AppCompatActivity {
    Button slogin,dlogin;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        slogin=findViewById(R.id.slogin);
        slogin.setOnClickListener(v -> Student_Number());

        dlogin=findViewById(R.id.dlogin);
        dlogin.setOnClickListener(v -> Driver_Home());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        /**if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Student_Number.class));
        }**/
    }

    public void Student_Number(){
        Intent intent = new Intent(this, Student_Number.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



    }
    public void Driver_Home(){
        Intent intent = new Intent(this, Driver_Home.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}