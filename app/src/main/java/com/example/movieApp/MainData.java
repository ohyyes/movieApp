package com.example.movieapp;

public class MainData {
    private int iv_profile;  //이미지뷰는 int 값임.
    private String tv_name, tv_rate, tv_gerne, tv_date, tv_running_time;

    //생성자
    public MainData(int iv_profile, String tv_name, String tv_rate, String tv_gerne, String tv_date, String tv_running_time) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_rate = tv_rate;
        this.tv_gerne = tv_gerne;
        this.tv_date = tv_date;
        this.tv_running_time = tv_running_time;

    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_rate() {
        return tv_rate;
    }

    public void setTv_rate(String tv_rate) {
        this.tv_rate = tv_rate;
    }

    public String getTv_gerne() {
        return tv_gerne;
    }

    public void setTv_gerne(String tv_gerne) {
        this.tv_gerne = tv_gerne;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_running_time() {
        return tv_running_time;
    }

    public void setTv_running_time(String tv_running_time) {
        this.tv_running_time = tv_running_time;
    }
}
