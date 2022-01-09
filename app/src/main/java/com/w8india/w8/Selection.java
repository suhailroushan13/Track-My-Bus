package com.w8india.w8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Selection extends AppCompatActivity {
    Button slogin,dlogin,button2;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        slogin=findViewById(R.id.slogin);
        slogin.setOnClickListener(v -> Student_Number());

        button2=findViewById(R.id.button2);
        button2.setOnClickListener(v ->Student_Number());






        dlogin=findViewById(R.id.dlogin);
        dlogin=findViewById(R.id.dlogin);
        dlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://trackmybus.tech/driver.html"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
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

}