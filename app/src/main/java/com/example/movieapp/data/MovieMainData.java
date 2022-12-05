package com.example.movieapp.data;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieMainData implements Parcelable {
    private int iv_poster;  //이미지뷰는 int 값임.
    private String tv_name, tv_rate, tv_date, tv_time, tv_gerne, tv_summary, tv_director, tv_actor;

    public MovieMainData(int iv_poster, String tv_name, String tv_rate, String tv_date, String tv_time, String tv_gerne, String tv_summary, String tv_director, String tv_actor) {
        this.iv_poster = iv_poster;
        this.tv_name = tv_name;
        this.tv_rate = tv_rate;
        this.tv_date = tv_date;
        this.tv_time = tv_time;
        this.tv_gerne = tv_gerne;
        this.tv_summary = tv_summary;
        this.tv_director = tv_director;
        this.tv_actor = tv_actor;
    }

    //생성자
    public MovieMainData() {
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

    public static final Creator<MovieMainData> CREATOR = new Creator<MovieMainData>() {
        @Override
        public MovieMainData createFromParcel(Parcel in) {
            return new MovieMainData(in);
        }

        @Override
        public MovieMainData[] newArray(int size) {
            return new MovieMainData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    //객체를 받아올때 호출
    protected MovieMainData(Parcel in) {
        tv_name = in.readString();
        iv_poster = in.readInt();
        tv_rate = in.readString();
        tv_date = in.readString();
        tv_time = in.readString();
        tv_gerne = in.readString();
        tv_summary = in.readString();
        tv_director = in.readString();
        tv_actor = in.readString();
    }
    //객체를 전달할 때 호출
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iv_poster);
        parcel.writeString(tv_name);
        parcel.writeString(tv_rate);
        parcel.writeString(tv_date);
        parcel.writeString(tv_time);
        parcel.writeString(tv_gerne);
        parcel.writeString(tv_summary);
        parcel.writeString(tv_director);
        parcel.writeString(tv_actor);
    }
}
