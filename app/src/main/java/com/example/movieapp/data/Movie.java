package com.example.movieapp.data;

import java.util.List;
//다영 - 규원이 로직 돌릴 때 mbti firebase에서 읽어오는 작업 시 필요
public class Movie {
    private String Title;
    private String IE;
    private String NS;
    private String TF;
    private String JP;
    private String Img;

    public Movie(){}

    public Movie(String Title, String ie, String ns, String tf, String jp, String img) {
        this.Title = Title;
        this.IE = ie;
        this.NS = ns;
        this.TF = tf;
        this.JP = jp;
        this.Img = img;
    }

    public String getTitle() {
        return Title;
    }

    public String getIE() {
        return IE;
    }

    public String getNS() {
        return NS;
    }

    public String getTF() {
        return TF;
    }

    public String getJP() {
        return JP;
    }

    public String getImg() {
        return Img;
    }
}

