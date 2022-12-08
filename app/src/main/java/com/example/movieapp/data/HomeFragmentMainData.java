package com.example.movieapp.data;

import android.graphics.Bitmap;

public class HomeFragmentMainData {
    private int mPoster;
    private Bitmap mPosterBitmap;
    private String mName;

    public HomeFragmentMainData(Bitmap mPosterBitmap, String mName) {
        this.mPosterBitmap = mPosterBitmap;
        this.mName = mName;
    }

    public int getmPoster() {
        return mPoster;
    }

    public void setmPoster(int mPoster) {
        this.mPoster = mPoster;
    }

    public Bitmap getmPosterBitmap() { return mPosterBitmap; }

    public void setmPosterBitmap(Bitmap mPosterBitmap) { this.mPosterBitmap = mPosterBitmap; }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


}