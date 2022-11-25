package com.example.movieapp;

public class MyPageFragmentMainData {
    private int iv_profile;  //이미지뷰는 int 값임.

    //생성자
    public MyPageFragmentMainData(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    //getter
    public int getIv_profile() {
        return iv_profile;
    }

    //setter
    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

}
