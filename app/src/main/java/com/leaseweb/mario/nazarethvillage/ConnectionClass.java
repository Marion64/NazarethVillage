package com.leaseweb.mario.nazarethvillage;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionClass {
    Connection conn;
    String classs = "com.mysql.jdbc.Driver";
    String ip = "172.20.10.14";
    String url = "jdbc:mysql://"+ip+"/nazarethvillage";
    String un = "nazarethvillage";
    String password = "123456";
    Statement st;



    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;


        try {

            Class.forName(classs).newInstance();

            conn = DriverManager.getConnection(url, un, password);

        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }

    public void Query(String sql){
        try
        {
            st = conn.createStatement();
            st.executeQuery(sql);
        } catch (Exception ex)
        {

        }
    }
}
