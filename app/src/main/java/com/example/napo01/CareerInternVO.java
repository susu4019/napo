package com.example.napo01;

import android.graphics.drawable.Drawable;

public class CareerInternVO {

    private String internName;
    private String internPer;
    private String internAct;

    public CareerInternVO(String internName, String internPer, String internAct) {
        this.internName = internName;
        this.internPer = internPer;
        this.internAct = internAct;
    }

    public String getInternName() {
        return internName;
    }
    public void setInternName(String internName) {
        this.internName = internName;
    }

    public String getInternPer() {
        return internPer;
    }
    public void setInternPer(String internPer) {
        this.internPer = internPer;
    }

    public String getInternAct() {
        return internAct;
    }
    public void setInternAct(String internAct) {
        this.internAct = internAct;
    }
}
