package com.example.napo01;

import android.graphics.drawable.Drawable;

public class CareerLangVo {

    private String kind;
    private String test;
    private String score;
    private String date;


    public CareerLangVo(String kind, String test, String score, String date) {
        this.kind = kind;
        this.test = test;
        this.score = score;
        this.date = date;

    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
