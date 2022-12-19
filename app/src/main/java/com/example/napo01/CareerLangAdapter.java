package com.example.napo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class CareerLangAdapter extends BaseAdapter {

    private ArrayList<CareerLangVo> langItems = new ArrayList<CareerLangVo>();

    @Override
    public int getCount() {
        return langItems.size();
    }

    @Override
    public Object getItem(int i) {
        return langItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.careerlang_lv, viewGroup, false);
        }

        CareerLangVo vo = langItems.get(i);
        EditText lang_kind = view.findViewById(R.id.lang_kind_lv);
        TextView lang_test = view.findViewById(R.id.lang_test_lv);
        EditText lang_score = view.findViewById(R.id.lang_score_lv);
        EditText lang_date = view.findViewById(R.id.lang_date_lv);

        lang_kind.setText(vo.getKind());
        lang_test.setText(vo.getTest());
        lang_score.setText(vo.getScore());
        lang_date.setText(vo.getDate());
        return view;
    }

    public void addItems(String lang_kind, String lang_test, String lang_score, String lang_date){
        CareerLangVo vo = new CareerLangVo(lang_kind, lang_test, lang_score, lang_date);
        langItems.add(vo);
    }


}
