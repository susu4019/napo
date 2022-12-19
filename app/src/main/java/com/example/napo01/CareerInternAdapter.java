package com.example.napo01;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class CareerInternAdapter extends BaseAdapter {

    private ArrayList<CareerInternVO> internItems = new ArrayList<CareerInternVO>();
    private Context context;

    @Override
    public int getCount() {
        return internItems.size();
    }

    @Override
    public Object getItem(int i) {
        return internItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        context = viewGroup.getContext();
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.careerintern_lv, viewGroup, false);
        }

        CareerInternVO vo = internItems.get(i);
        EditText internName = view.findViewById(R.id.internName_lv);
        EditText internPer = view.findViewById(R.id.internPer_lv);
        EditText internAct = view.findViewById(R.id.internAct_lv);
        internName.setText(vo.getInternName());
        internPer.setText(vo.getInternPer());
        internAct.setText(vo.getInternAct());

      return view;
    }

    public void addItems(String name, String per, String act){
        CareerInternVO vo = new CareerInternVO(name, per, act);
        internItems.add(vo);
    }
}
