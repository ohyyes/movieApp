package com.example.movieapp;

public class ReviewFragmentMainData {
    private int iv_profile, tv_my_rate;  //이미지뷰는 int 값임.
    private String tv_name, tv_review_date, tv_review;

    public ReviewFragmentMainData(int iv_profile, String tv_name,int tv_my_rate,  String tv_review_date, String tv_review) {
        this.iv_profile = iv_profile;
        this.tv_my_rate = tv_my_rate;
        this.tv_name = tv_name;
        this.tv_review_date = tv_review_date;
        this.tv_review = tv_review;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public int getTv_my_rate() {
        return tv_my_rate;
    }

    public void setTv_my_rate(int tv_my_rate) {
        this.tv_my_rate = tv_my_rate;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_review_date() {
        return tv_review_date;
    }

    public void setTv_review_date(String tv_review_date) {
        this.tv_review_date = tv_review_date;
    }

    public String getTv_review() {
        return tv_review;
    }

    public void setTv_review(String tv_review) {
        this.tv_review = tv_review;
    }
}

