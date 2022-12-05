package com.example.movieapp;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SearchFragmentMainData implements Serializable {
    private Bitmap poster;
    private String title, userRating, genre, openYear, runningTime, director, actors, story;

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

    public String getDirector() { return  director; }
    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() { return  actors; }
    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getStory() { return story; }
    public void setStory(String story) { this.story = story; }
}