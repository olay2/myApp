package com.example.myapp;
import java.sql.*;

public class DBConnect {
    public static Connection getConnect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String user ="olay";
            String pwd="12345678";
            String url="jdbc:mysql://192.168.100.3:3306/cw2?characterEncoding=utf8";//ip wifi
            Connection c = DriverManager.getConnection(url,user,pwd);
            return c;

        }catch (Exception ex){
            return null;
        }
    }


}
