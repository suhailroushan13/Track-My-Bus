package com.w8india.w8;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Request extends AppCompatActivity {
  EditText names,messgaes;
    String waittime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        names=findViewById(R.id.names);

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