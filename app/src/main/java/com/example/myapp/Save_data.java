package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.sql.*;

public class Save_data extends AppCompatActivity {
    EditText txtname,txtsurname,txttel;
    TextView txtshowcon;
    Button btnok,btncancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);
        getSupportActionBar().setTitle("MySqlApp");
        txtname = findViewById(R.id.txtname);
        txtsurname = findViewById(R.id.txtsurname);
        txttel = findViewById(R.id.txttel);
        txtshowcon = findViewById(R.id.txtcon);
        btnok = findViewById(R.id.ok);
        btncancle = findViewById(R.id.cancle);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new myData().execute("");
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



    }

    private DialogInterface getDialog() {
        return null;
    }

    private class myData extends AsyncTask<String,String,String>{
        String msg="";
        String name =txtname.getText().toString();
        String surname = txtsurname.getText().toString();
        String tel = txttel.getText().toString();

        @Override
        protected void onPreExecute() {
            txtshowcon.setText("Please wait connecting.....");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Connection c = DBConnect.getConnect();
                ModelCW f = new ModelCW(c);
                f.setName(name);
                f.setSurname(surname);
                f.setTel(tel);
                int r = f.InsertData();
                if(r>0){
                    msg="Insert data complate";
                } else {
                    msg = "Can not Save Data";

                }

            }catch (Exception ex){
                msg = "Fail....";

            }
            return msg;

        }

        @Override
        protected void onPostExecute(String s) {

            txtshowcon.setText(s);
            txtname.setText("");
            txtsurname.setText("");
            txttel.setText("");
            txtname.requestFocus();


        }
    }

}