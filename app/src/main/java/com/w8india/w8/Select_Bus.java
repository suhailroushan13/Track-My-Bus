package com.w8india.w8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

    Button bus1,bus2,bus3,bus4,bus5,bus6,bus7;
    String [] titles={"Bus No.1 ","Bus No.2","Bus No.3","Bus No.4","Bus No.5","Bus No.6","Bus No.7"};
    String [] descriptions={"JNTUH - MEHDIPATNAM - COLLEGE","BANDLAGUDA - SHALIBANDA - COLLEGE","NERDMERT X ROAD - LAKDIKAPOOL - COLLEGE","LB NAGAR - NAMPALLY - COLLEGE","BAI RAMAL GUDA - RAJENDRA NAGAR - COLLEGE","                  Not in Service","MIYAPUR - LANGER HOUSER - COLLEGE"};
    int [] images={R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b};
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__bus);
        lv =  findViewById(R.id.bus_list);
        MyAdapter adapter = new MyAdapter(Select_Bus.this,titles,descriptions,images);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Select_Bus.this, Home.class));
            }
        });
    }
    public void Home(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    class MyAdapter extends ArrayAdapter {
        int[] imageArray;
        String[] titleArray;
        String[] descArray;
        public MyAdapter(Context context, String[] titles, String[] descriptions, int [] img) {
            //Overriding Default Constructor off ArratAdapter
            super(context, R.layout.example_cuslistview_row,R.id.idTitle,titles);
            this.imageArray=img;
            this.titleArray=titles;
            this.descArray=descriptions;

        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Inflating the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.example_cuslistview_row,parent,false);
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