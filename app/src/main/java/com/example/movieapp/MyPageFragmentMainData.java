package com.example.movieapp;

public class MyPageFragmentMainData {
    private int iv_profile;  //이미지뷰는 int 값임.
    private String tv_name;
    //생성자
    public MyPageFragmentMainData(int iv_profile, String tv_name) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
    }

    //getter
    public int getIv_profile() {
        return iv_profile;
    }
    //setter
    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }
    //setter
    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }
}
