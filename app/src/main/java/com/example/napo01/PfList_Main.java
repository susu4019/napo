package com.example.napo01;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatWidthException;

public class PfList_Main extends AppCompatActivity {
    private ListView pf_List;
    private PfListAdapter pfListAdapter;
    private Button btn_pfPlus;
    private TextView pdf;
    private IllegalFormatWidthException relativeLayout;
    private String dirpath;
    private Display display;
    private Point size;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pofollist_in);

        Log.d("check", "확인");

        pf_List = findViewById(R.id.pf_list);
        pdf = findViewById(R.id.pdf);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getRealSize(size);
        pfListAdapter = new PfListAdapter(size.x, size.y);
        pf_List.setAdapter(pfListAdapter);
        pfListAdapter.addItems(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo), "2022-08-20", "내 포트폴리오 1");
        pfListAdapter.notifyDataSetChanged();
        Intent intent = getIntent();
        String tv_date = intent.getStringExtra("date");
        String tv_name = intent.getStringExtra("name");
        pf_List.setAdapter(pfListAdapter);
        pfListAdapter.addItems(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slide1), getTime(), "myPDF");
        pfListAdapter.notifyDataSetChanged();
  }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

