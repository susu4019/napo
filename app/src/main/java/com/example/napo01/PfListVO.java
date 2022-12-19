package com.example.napo01;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class PfListVO {
    private Drawable pf_img;
    private String date;
    private String title;

    public PfListVO(Drawable pf_img, String date, String title) {
        this.pf_img = pf_img;
        this.date = date;
        this.title = title;
    }

    public Drawable getPf_img() {
        return pf_img;
    }

    public void setPf_img(Drawable pf_img) {
        this.pf_img = pf_img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
