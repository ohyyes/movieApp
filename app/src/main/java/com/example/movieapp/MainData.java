package com.example.movieapp;

import android.graphics.Bitmap;

public class MainData {
    private Bitmap poster;
    private String title, userRating, genre, openYear, runningTime, directors, actors;

    //생성자
    public MainData() {}
    public MainData(Bitmap poster, String title, String userRating, String genre, String openYear, String runningTime) {
        this.poster = poster;
        this.title = title;
        this.userRating = userRating;
        this.genre = genre;
        this.openYear = openYear;
        this.runningTime = runningTime;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getDirectors() { return  directors; }
    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() { return  actors; }
    public void setActors(String actors) {
        this.actors = actors;
    }
}