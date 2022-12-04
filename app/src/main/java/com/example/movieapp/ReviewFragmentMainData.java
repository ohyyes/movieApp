package com.example.movieapp;

import java.util.Comparator;

public class ReviewFragmentMainData implements Comparator<ReviewFragmentMainData> {
    private int iv_poster, tv_my_rate;  //이미지뷰는 int 값임.
    private String tv_name, tv_review_date, tv_review;
    private boolean isSelected = false;
    public ReviewFragmentMainData(int iv_poster, String tv_name,int tv_my_rate,  String tv_review_date, String tv_review) {
        this.iv_poster = iv_poster;
        this.tv_my_rate = tv_my_rate;
        this.tv_name = tv_name;
        this.tv_review_date = tv_review_date;
        this.tv_review = tv_review;
    }

    public ReviewFragmentMainData(int iv_poster, String tv_name,int tv_my_rate,  String tv_review_date, String tv_review, boolean isSelected) {
        this.iv_poster = iv_poster;
        this.tv_my_rate = tv_my_rate;
        this.tv_name = tv_name;
        this.tv_review_date = tv_review_date;
        this.tv_review = tv_review;
        this.isSelected = isSelected;
    }

    public int getIv_poster() {
        return iv_poster;
    }

    public void setIv_poster(int iv_poster) {
        this.iv_poster = iv_poster;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    //비교 함수 사용을 위해 함수 오버라이딩
    @Override
    public int compare(ReviewFragmentMainData reviewFragmentMainData, ReviewFragmentMainData t1) {
        return 0;
    }
}

