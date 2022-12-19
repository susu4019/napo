package com.example.napo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//자격증 정보 Adapter
public class CareerCertAdapter extends BaseAdapter {
    private ArrayList<CareerCertVO> certItems = new ArrayList<CareerCertVO>();
    public ArrayList<CareerCertVO> getCertItems(){
        return certItems;
    }


    @Override
    public int getCount() {
        return certItems.size();
    }

    @Override
    public Object getItem(int i) {
        return certItems.get(i);
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
            view = inflater.inflate(R.layout.careercert_lv, viewGroup, false);
        }

        CareerCertVO vo = certItems.get(i);
        TextView certName = view.findViewById(R.id.certNamelv);
        TextView certInst = view.findViewById(R.id.certInstlv);
        TextView certDate = view.findViewById(R.id.certDatelv);

        certName.setText(vo.getCertName());
        certInst.setText(vo.getCertInst());
        certDate.setText(vo.getCertDate());

        return view;
    }

    public void addItems(String cert_ser, String certInst, String certDate){
        CareerCertVO vo = new CareerCertVO(cert_ser, certInst, certDate);
        certItems.add(vo);
    }
}
