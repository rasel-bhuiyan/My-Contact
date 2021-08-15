package com.example.my_contact;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Model_Class implements Serializable {
    private int id;
    private  String name;
    private  String number;
    private  Bitmap image;

    public Model_Class(String name, String number, Bitmap image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public Model_Class(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Model_Class(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
