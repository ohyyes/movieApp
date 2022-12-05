package com.example.movieapp;

public class HomeFragmentMainData {
    private int mPoster;
    private String mName;

    public HomeFragmentMainData(int mPoster, String mName) {
        this.mPoster = mPoster;
        this.mName = mName;
    }

    public int getmPoster() {
        return mPoster;
    }

    public void setmPoster(int mPoster) {
        this.mPoster = mPoster;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


}