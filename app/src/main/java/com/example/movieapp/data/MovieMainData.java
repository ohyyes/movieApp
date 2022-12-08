package com.example.movieapp.data;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MovieMainData implements Serializable {
    private int poster;
    private Bitmap posterBitmap;
    private String title, userRating, openYear, runningTime, genre, summary, director, actors, review;

    public MovieMainData(int poster, String title, String userRating, String openYear, String runningTime, String genre, String summary, String director, String actors) {
        this.poster = poster;
        this.title = title;
        this.userRating = userRating;
        this.openYear = openYear;
        this.runningTime = runningTime;
        this.genre = genre;
        this.summary = summary;
        this.director = director;
        this.actors = actors;
    }

    public MovieMainData() {}
    public MovieMainData(int poster, String title) {
        this.poster = poster;
        this.title = title;
    }
//    public static final Creator<MovieMainData> CREATOR = new Creator<MovieMainData>() {
//        @Override
//        public MovieMainData createFromParcel(Parcel in) {
//            return new MovieMainData(in);
//        }
//
//        @Override
//        public MovieMainData[] newArray(int size) {
//            return new MovieMainData[size];
//        }
//    };

    //getter
    public int getPoster() {
        return poster;
    }
    //setter
    public void setPoster(int poster) {
        this.poster = poster;
    }

    public Bitmap getPosterBitmap() { return posterBitmap; }

    public void setPosterBitmap(Bitmap posterBitmap) { this.posterBitmap = posterBitmap; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getOpenYear() {
        return openYear;
    }

    public void setOpenYear(String openYear) {
        this.openYear = openYear;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
    //객체를 받아올때 호출
    protected MovieMainData(Parcel in) {
        title = in.readString();
        poster = in.readInt();
        userRating = in.readString();
        openYear = in.readString();
        runningTime = in.readString();
        genre = in.readString();
        summary = in.readString();
        director = in.readString();
        actors = in.readString();
    }
//    //객체를 전달할 때 호출
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(poster);
        parcel.writeString(title);
        parcel.writeString(userRating);
        parcel.writeString(openYear);
        parcel.writeString(runningTime);
        parcel.writeString(genre);
        parcel.writeString(summary);
        parcel.writeString(director);
        parcel.writeString(actors);
    }
}
