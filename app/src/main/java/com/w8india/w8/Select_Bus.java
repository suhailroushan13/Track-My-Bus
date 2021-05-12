package com.w8india.w8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Select_Bus extends AppCompatActivity {

    SharedPreferences preferences;
    Button bus1,bus2,bus3,bus4,bus5,bus6,bus7;
    String [] titles={"Bus No.1 ","Bus No.2","Bus No.3","Bus No.4","Bus No.5","Bus No.6","Bus No.7"," Bus No.8"};
    String [] descriptions={"JNTU - AMEERPET - MEHDIPATNAM","CHANDRAYANGUTTA - SHALIBANDA - RAJENDRANAGAR","NEREDMERT - MUSHEERABAD - MDPTM","LB NAGAR - NAMPALLY - BAPUGHAT","SAGAR X ROAD - BAHADURPURA - RJNR","NANAL NAGAR - 7 TOMBS- GOLCONDA","MIYAPUR - GACHIBOWLI - LANGER HOUSE", "IS SADAN - DABIRPURA - KARWAN"};
    int [] images={R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver,R.drawable.busdriver};
    ListView lv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__bus);
        lv =  findViewById(R.id.bus_list);
        MyAdapter adapter = new MyAdapter(Select_Bus.this,titles,descriptions,images);
        lv.setAdapter(adapter);
        preferences = getSharedPreferences("busno",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               switch (position){
                   case 0:
                       editor.putInt("bus",1);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 1:
                       editor.putInt("bus",2);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 2:
                       editor.putInt("bus",3);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 3:
                       editor.putInt("bus",4);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 4:
                       editor.putInt("bus",5);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 5:
                       editor.putInt("bus",6);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 6:
                       editor.putInt("bus",7);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;
                   case 7:
                       editor.putInt("bus",8);
                       editor.commit();
                       startActivity(new Intent(Select_Bus.this, Home.class));
                       finish();
                       break;

               }
            }
        });
    }



    class MyAdapter extends ArrayAdapter {
        int[] imageArray;
        String[] titleArray;
        String[] descArray;
        public MyAdapter(Context context, String[] titles, String[] descriptions, int [] img) {
            //Overriding Default Constructor off ArratAdapter
            super(context, R.layout.select_bus_listview,R.id.idTitle,titles);
            this.imageArray=img;
            this.titleArray=titles;
            this.descArray=descriptions;

        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {






            //Inflating the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.select_bus_listview,parent,false);
            //Get the reference to the view objects
            ImageView myImage =  row.findViewById(R.id.idPic);
            TextView myTitle =  row.findViewById(R.id.idTitle);
            TextView myDescription =  row.findViewById(R.id.idDescription);
            //Providing the element of an array by specifying its position
            myImage.setImageResource(imageArray[position]);
            myTitle.setText(titleArray[position]);
            myDescription.setText(descArray[position]);
            return row;



        }



    }
}