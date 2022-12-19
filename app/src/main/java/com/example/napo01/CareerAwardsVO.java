package com.example.napo01;

import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;


public class CareerAwardsVO {
    private String name;
    private String inst;
    private String date;

    public CareerAwardsVO(String name, String inst, String date) {
        this.name = name;
        this.inst = inst;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
