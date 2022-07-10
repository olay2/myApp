package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class manageData extends AppCompatActivity {
    EditText manageID, name, surname,tel;
    Button btnUpdate, btndelect;
    TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);
        getSupportActionBar().setTitle("MySQL");
        manageID = findViewById(R.id.manageID);
        name = findViewById(R.id.manaName);
        surname = findViewById(R.id.manegeSurname);
        tel = findViewById(R.id.manaTel);
        btnUpdate = findViewById(R.id.btnUpdata);
        btndelect = findViewById(R.id.btnDelect);
        tvshow =findViewById(R.id.manageshow);

        new selectID().execute("");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new update().execute("");
            }
        });

        btndelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new delete().execute("");
            }
        });


//        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();

    }
    private class update extends AsyncTask<String,String,String>{
        String mess = "";
        String id  = manageID.getText().toString();
        String fname = name.getText().toString();
        String sur = surname.getText().toString();
        String telephone = tel.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Connection c = DBConnect.getConnect();
                ModelCW cw = new ModelCW(c);
                cw.setName(fname);
                cw.setSurname(sur);
                cw.setTel(telephone);
                cw.setId(id);
                int row = cw.UpdateData();
                if(row != 0 ){
                    mess = "ອັບເດດແລ້ວ";
                }else {
                    mess = "ບໍ່ສາມາດອັບເດດ";
                }

            }catch (Exception ex){
                mess = "ເກີດຂໍ້ຜິດພາດ";
            }
            return mess;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            Intent n = new Intent(manageData.this,Show_data.class);
            startActivity(n);
        }
    }

    private class delete extends AsyncTask<String,String,String>{
        String mess = "";
        String id  = manageID.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Connection c = DBConnect.getConnect();
                ModelCW cw = new ModelCW(c);
                cw.setId(id);
                int row = cw.DeleteData();
                if(row != 0 ){
                    mess = "ລົບແລ້ວ";
                }else {
                    mess = "ບໍ່ສາມາດລົຍໄດ້";
                }

            }catch (Exception ex){
                mess = "ເກີດຂໍ້ຜິດພາດ";
            }
            return mess;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            Intent n = new Intent(manageData.this,Show_data.class);
            startActivity(n);
        }
    }
    private class selectID extends AsyncTask<String,String,String>{
        Intent bundle = getIntent();
        //        manageID.setText(bundle.getString("id"));
        String id = bundle.getStringExtra("id");

        String msg = "";
        @Override
        protected void onPreExecute() {
            tvshow.setText("ລໍຖ້າບຸດຫນຶ່ງ");
            //            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnect();
                ModelCW cw = new ModelCW(c);
                cw.setId(id);
                ResultSet rs = cw.SelectDataById();
                while (rs.next()){
                    manageID.setText(rs.getString("id"));
                    name.setText(rs.getString("name"));
                    surname.setText(rs.getString("surname"));
                    tel.setText(rs.getString("tel"));
                }
                rs.close();
            }catch (Exception ex){
                msg="ຜິດພາດ";
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            tvshow.setText(s);
        }
    }
}