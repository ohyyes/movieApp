package com.example.movieapp;

public class MovieItem {
    private int img;
    private String name;

    public MovieItem(int mImg, String mName) {
        this.img = mImg;
        this.name = mName;
    }

    //Getter
    public int getImg() { return img;}

    public String getName() { return name; }

    //Setter
    public void setImg(int mImg) { this.img = mImg; }

    public void setName(String mName) { this.name = mName; }
}
