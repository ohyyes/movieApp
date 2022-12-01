package com.example.movieapp;

import android.graphics.Bitmap;

public class ItemData {
    private Bitmap mPoster;
    private String mName;

    public ItemData(Bitmap mPoster, String mName) {
        this.mPoster = mPoster;
        this.mName = mName;
    }

    public Bitmap getmPoster() {
        return mPoster;
    }

    public void setmPoster(Bitmap mPoster) {
        this.mPoster = mPoster;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


}