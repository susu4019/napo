package com.example.napo01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;
    private PageChangeListener listener;

    public MyAdapter(FragmentActivity fa, int count, PageChangeListener listener) {
        super(fa);
        mCount = count;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("MyAdapter", position+"");
        int index = getRealPosition(position);
        Log.d("MyAdapter", index+"");

        Bundle bdl = new Bundle();
        bdl.putSerializable("listener",listener);

        if(index==0){
            Fragment_1 f = new Fragment_1();
            bdl.putInt("frag",1);
            f.setArguments(bdl);
            return f;
        }
        else if(index==1){
            Fragment_2 f = new Fragment_2();
            bdl.putInt("frag",2);
            f.setArguments(bdl);
            return f;
        }
        else if(index==2){
            Fragment_3 f = new Fragment_3();
            bdl.putInt("frag",3);
            f.setArguments(bdl);
            return f;
        }
        else if(index==3){
            Fragment_4 f = new Fragment_4();
            bdl.putInt("frag",4);
            f.setArguments(bdl);
            return f;
        }
        else if(index==4){
            Fragment_5 f = new Fragment_5();
            bdl.putInt("frag",5);
            f.setArguments(bdl);
            return f;
        }
        else{
            Fragment_6 f = new Fragment_6();
            bdl.putInt("frag",6);
            f.setArguments(bdl);
            return f;
        }
        // listener.onPagechange(index+1);
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }
}