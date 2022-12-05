package com.example.movieapp;

public class MovieDetailFragmentMainData {
    private int iv_poster;  //이미지뷰는 int 값임.
    private String tv_name, tv_rate, tv_date, tv_time, tv_gerne, tv_summary, tv_director, tv_actor;

    //생성자
    public MovieDetailFragmentMainData() {
        this.iv_poster = 0;
        this.tv_name = null;
        this.tv_rate = null;
        this.tv_date = null;
        this.tv_time = null;
        this.tv_gerne = null;
        this.tv_summary = null;
        this.tv_director = null;
        this.tv_actor = null;
    }

    //getter
    public int getIv_poster() {
        return iv_poster;
    }
    //setter
    public void setIv_poster(int iv_poster) {
        this.iv_poster = iv_poster;
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

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_rate = tv_date;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }

    public String getTv_gerne() {
        return tv_gerne;
    }

    public void setTv_gerne(String tv_gerne) {
        this.tv_gerne = tv_gerne;
    }

    public String getTv_summary() {
        return tv_summary;
    }

    public void setTv_summary(String tv_summary) {
        this.tv_summary = tv_summary;
    }

    public String getTv_director() {
        return tv_director;
    }

    public void setTv_director(String tv_director) {
        this.tv_director = tv_director;
    }


    public String getTv_actor() {
        return tv_actor;
    }

    public void setTv_actor(String tv_actor) {
        this.tv_actor = tv_actor;
    }

}
