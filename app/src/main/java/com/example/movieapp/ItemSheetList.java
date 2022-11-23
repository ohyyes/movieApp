package com.example.movieapp;

public class ItemSheetList {
    String song_name;
    String singer;

    public ItemSheetList(String song_name, String singer) {
        this.song_name = song_name;
        this.singer = singer;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}