package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;

import java.util.ArrayList;
import java.sql.*;

public class Show_data extends AppCompatActivity {
    Button btnshow;
    ListView listv;
    TextView tvinfo;
    ArrayList arrname = new ArrayList();
    ArrayList arrid=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        getSupportActionBar().setTitle("CRUD MySQL");
        btnshow = findViewById(R.id.btnshow);
        listv = findViewById(R.id.lv);
        tvinfo = findViewById(R.id.txtview);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getData().execute("");
            }
        });
    }
    private class getData extends AsyncTask<String,String,String>{
        ArrayAdapter<String> ad;
        String msg = "";
        @Override
        protected void onPreExecute() {
            tvinfo.setText("Please waiting....");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnect();
                msg ="Connect Database Success";
                ModelCW cw = new ModelCW(c);
                ResultSet rs = cw.SelectData();
                arrid.clear();
                arrname.clear();
                while (rs.next()){
                    arrid.add(rs.getString("id"));
                    arrname.add(rs.getString("name"));
                }
                rs.close();

            }catch (Exception ex){
                msg ="can not load data";

            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrname);
            listv.setAdapter(ad);
            listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   String str = arrid.get(position).toString();
                    Intent n = new Intent(Show_data.this,manageData.class);
                    n.putExtra("id",str);
                    startActivity(n);
                }
            });
        }
    }
}