package com.leaseweb.mario.nazarethvillage;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Shopitem implements Serializable {
    private int id;
    private Bitmap image;
    private String category;
    private String name;
    private String description;
    private String price;

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }



    public Shopitem(int id,Bitmap image,String category, String name, String description,String price){
        this.id = id;
        this.category = category;
        this.image=image;
        this.name=name;
        this.description = description;
        this.price = price;


    }







}
