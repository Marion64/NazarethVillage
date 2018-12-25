package com.leaseweb.mario.nazarethvillage;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity implements Serializable {

    Bitmap img;
    String title;
    String content;

    //String title = intent.getStringExtra("Title");
   // String content = (String) intent.getStringExtra("Content");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView image = (ImageView) findViewById(R.id.imageView3);
        TextView header = (TextView) findViewById(R.id.textView3);
        TextView description = (TextView) findViewById(R.id.textView4);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                img = null;
                title = null;
                content = null;
            } else {
                img = extras.getParcelable("Image");
                title = extras.getString("Title");
                content = extras.getString("Content");
                image.setImageBitmap(img);
                header.setText(title);
                description.setText(content);
            }
        } else {
            img = null;
            title = (String) savedInstanceState.getSerializable("Title");
            content = (String) savedInstanceState.getSerializable("Content");
        }


        /*

        image.setImageBitmap(bitmap);

        description.setText(content);*/
    }
}
