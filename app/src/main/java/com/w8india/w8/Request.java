package com.w8india.w8;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Array;

public class Request extends AppCompatActivity {
  EditText names,messgaes;
  Button call,wa;
    String waittime,number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        names=findViewById(R.id.names);

        messgaes = findViewById(R.id.messages);
        call = findViewById(R.id.callbtn);
        wa = findViewById(R.id.whatsappbtn);
        SharedPreferences preferences = getSharedPreferences("bus",MODE_PRIVATE);
        switch (preferences.getInt("bus",0)){
            case 1:
                number="9966255198";


                break;
            case 2:
                number="9959707274";


                break;
            case 3:
                number="9392413957";

                break;

            case 4:
                number="7995726523";

                break;
            case 5:
                number="9581991734";

                break;
            case 6:
                number = "9618211626";

                break;
            case 7:
                number="7095175669";

                break;
            case 8:

                number="9912235254";


                break;


        }        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Request.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    Request.this.startActivity(callIntent);
                }else{
                    Toast.makeText(Request.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://wa.me/91"+number+"?text="+names.getText().toString()+messgaes.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        Spinner spinner = findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.buses, R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        waittime = "5 Minutes";
                        if(names.getText().toString().isEmpty()){
                            names.setError("Name is Required");
                        }



                        break;
                    case 1:
                        waittime = "10 Minutes";
                        if(names.getText().toString().isEmpty()){
                            names.setError("Name is Required");
                        }


                        break;
                    case 2:
                        waittime = "15 Minutes";
                        if(names.getText().toString().isEmpty()){
                            names.setError("Name is Required");
                        }


                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }
}