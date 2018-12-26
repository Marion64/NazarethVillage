package com.leaseweb.mario.nazarethvillage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements Serializable {

    Bitmap img;
    String title;
    String content;
    ArrayList<Story> SpecificData = null;
    GetSpecificData mydata;
    int index;
    String sindex;
   // String content = (String) intent.getStringExtra("Content");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            int extras = getIntent().getIntExtra("index",0);
            if(extras == 0) {
                index= 0;
            } else {
                index = extras;
            }
        } else {
            index=0;
        }
        ImageView image = (ImageView) findViewById(R.id.imageView3);
        TextView header = (TextView) findViewById(R.id.textView3);
        TextView description = (TextView) findViewById(R.id.textView4);
        image.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        sindex = ""+index;
        GetSData();





        /*

        image.setImageBitmap(bitmap);

        description.setText(content);*/
    }

    private void GetSData(){
        class GetSData extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            TextView header = (TextView) findViewById(R.id.textView3);
            TextView description = (TextView) findViewById(R.id.textView4);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailsActivity.this, "Loading","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(SpecificData.size()>0){
                    image.setImageBitmap(SpecificData.get(0).getImage());
                    header.setText(SpecificData.get(0).getTitle());
                    description.setText(SpecificData.get(0).getContent());
                    image.setVisibility(View.VISIBLE);
                    header.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    loading.dismiss();
                }else{
                    loading.dismiss();
                    Toast.makeText(DetailsActivity.this,"No Connection",Toast.LENGTH_LONG).show();
                }









            }

            @Override
            protected String doInBackground(String... params) {
                GetSpecificData mydata = new GetSpecificData();
                SpecificData = mydata.doInBackground(sindex);
                return null;

            }

        }
        GetSData gsd = new GetSData();
        gsd.execute();
    }
}
