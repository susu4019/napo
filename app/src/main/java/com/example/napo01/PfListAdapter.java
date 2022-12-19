package com.example.napo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PfListAdapter extends BaseAdapter {
    private ArrayList<PfListVO> pfItems = new ArrayList<PfListVO>();
    String dirpath;
    View itemView;
    Context context;
    int width;
    int height;

    public PfListAdapter(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public int getCount() {
        return pfItems.size();
    }

    @Override
    public Object getItem(int i) {
        return pfItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pofollist_in_lv, viewGroup, false);
            itemView = view;
        }

        PfListVO vo = pfItems.get(i);
        ImageView pf_img = view.findViewById(R.id.pf_img);
        TextView coDate = view.findViewById(R.id.tv_coDate);
        EditText pfTitle = view.findViewById(R.id.edt_pfTitle);
        TextView pdf = view.findViewById(R.id.pdf);

        pf_img.setImageDrawable(vo.getPf_img());
        coDate.setText(vo.getDate());
        pfTitle.setText(vo.getTitle());

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).startActivity(new Intent(context.getApplicationContext(), Ppt_exam.class));
            }
        });
        return itemView;
    }


    public void addItems(Drawable img, String date, String title) {
        PfListVO vo = new PfListVO(img, date, title);
        pfItems.add(vo);
    }
}




