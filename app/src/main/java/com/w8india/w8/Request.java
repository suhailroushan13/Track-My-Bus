package com.w8india.w8;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Request extends AppCompatActivity {
  EditText names,messgaes;
  Button call,wa;
    String waittime = "5 minutes",number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        names=findViewById(R.id.names);

        messgaes = findViewById(R.id.messages);
        call = findViewById(R.id.sms);
        wa = findViewById(R.id.whatsappbtn);
        SharedPreferences preferences = getSharedPreferences("busno",MODE_PRIVATE);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Request.this, R.style.ThemeOverlay_App_MaterialAlertDialog);

                builder.setPositiveButton("Proceed",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("tel:"+number));
                        Request.this.startActivity(i);



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Do you want to call?");
                builder.setMessage("Please note that this may distract the driver.\nSo please use this carefully and\nOnly use if its Very Urgent!");
                builder.create();
                builder.show();

            }
        });

        wa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(names.getText().toString().isEmpty()) {

                    String msg = "-";
                    if(!messgaes.getText().toString().isEmpty()){
                        msg = messgaes.getText().toString();
                    }
                    Uri uri = Uri.parse("https://wa.me/91" + number + "?text=Name: "  + names.getText().toString() +"\n Reason: "+ msg + "\nRequested Time: "+waittime);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else{
                    names.setError("Name is Required");
                }

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