package com.example.movieapp;

public class RecFragmentMainData {
    private String img;
    private String name;

    public RecFragmentMainData(String mImg, String mName) {
        this.img = mImg;
        this.name = mName;
    }

    //Getter
    public String getImg() { return img;}

    public String getName() { return name; }

    //Setter
    public void setImg(String mImg) { this.img = mImg; }

    public void setName(String mName) { this.name = mName; }
}
