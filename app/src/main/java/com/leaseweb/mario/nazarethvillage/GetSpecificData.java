package com.leaseweb.mario.nazarethvillage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetSpecificData {

    ConnectionClass connectionClass;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    Story story;
    ArrayList<Story> stories = new ArrayList<Story>();

    public ArrayList<Story> doInBackground(String id) {
        int parse = Integer.parseInt(id);
        ArrayList<Story> data = null;
        data = new ArrayList<Story>();
        connectionClass = new ConnectionClass();
        try
        {

            Connection conn = connectionClass.CONN();// Connect to database
            if (conn == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from story where idstory ="+parse;
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    story = new Story(rs.getInt("idstory"),getImage(rs.getString("image"),rs.getString("extension")), rs.getString("title"), rs.getString("content"));

                    data.add(story);
                }


                ConnectionResult = "successful";
                isSuccess=true;
                conn.close();
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }

        return data;
    }

    protected Bitmap getImage(String params, String ext) {
        String name = params;
        String format = ext;
        try {
            URL url = new URL("http://"+connectionClass.ip+"/NazarethVillage/"+name+"."+format);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
