package com.example.movieapp.data;

import android.graphics.Bitmap;
import android.os.Parcel;

import java.io.Serializable;
import java.util.Comparator;

public class ReviewMainData implements Comparator<ReviewMainData>, Serializable {
    private int iv_poster;//이미지뷰는 int 값임.
    private Bitmap iv_posterBitmap;
    private String tv_my_rate;
    private String tv_name, tv_review_date, tv_review;
    private boolean isSelected = false;

    public ReviewMainData(int iv_poster, String tv_name, String tv_my_rate, String tv_review_date, String tv_review) {
        this.iv_poster = iv_poster;
        this.tv_my_rate = tv_my_rate;
        this.tv_name = tv_name;
        this.tv_review_date = tv_review_date;
        this.tv_review = tv_review;
    }

    public ReviewMainData(Bitmap iv_posterBitmap, String tv_name, String tv_my_rate, String tv_review_date, String tv_review) {
        this.iv_posterBitmap = iv_posterBitmap;
        this.tv_my_rate = tv_my_rate;
        this.tv_name = tv_name;
        this.tv_review_date = tv_review_date;
        this.tv_review = tv_review;
    }

//    protected ReviewMainData(Parcel in) {
//        iv_poster = in.readInt(); //이거 맞냐? - 유다
//        tv_my_rate = in.readString();
//        tv_name = in.readString();
//        tv_review_date = in.readString();
//        tv_review = in.readString();
//        isSelected = in.readByte() != 0;
//    }

//    public static final Creator<ReviewMainData> CREATOR = new Creator<ReviewMainData>() {
//        @Override
//        public ReviewMainData createFromParcel(Parcel in) {
//            return new ReviewMainData(in);
//        }
//
//        @Override
//        public ReviewMainData[] newArray(int size) {
//            return new ReviewMainData[size];
//        }
//    };

    public Integer getIv_poster() {
        return iv_poster;
    }

    public void setIv_poster(Integer iv_poster) {
        this.iv_poster = iv_poster;
    }

    public String getTv_my_rate() {
        return tv_my_rate;
    }

    public void setTv_my_rate(String tv_my_rate) {
        this.tv_my_rate = tv_my_rate;
    }

    public Bitmap getIv_posterBitmap() {
        return iv_posterBitmap;
    }

    public void setIv_posterBitmap(Bitmap iv_posterBitmap) {
        this.iv_posterBitmap = iv_posterBitmap;
    }

    public void setIv_poster(int iv_poster) {
        this.iv_poster = iv_poster;
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
    public int compare(ReviewMainData reviewMainData, ReviewMainData t1) {
        return 0;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(iv_poster);
//        parcel.writeDouble(tv_my_rate);
//        parcel.writeString(tv_name);
//        parcel.writeString(tv_review_date);
//        parcel.writeString(tv_review);
//        parcel.writeByte((byte) (isSelected ? 1 : 0));
//    }
}

