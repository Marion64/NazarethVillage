package com.leaseweb.mario.nazarethvillage;

import android.graphics.Bitmap;

public class Story {
    private Bitmap image;
    private String title;
    private String content;

    public Bitmap getImage() {
        return image;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Story(Bitmap image, String title, String content){
        this.image=image;
        this.title=title;
        this.content=content;


    }







}
