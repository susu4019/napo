package com.example.napo01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import me.relex.circleindicator.CircleIndicator3;

public class Ppt_exam extends AppCompatActivity implements PageChangeListener, Parcelable {

    private static final long serialVersionUID = -5210739585384970789L;
    private boolean isVisible;

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 6;
    private CircleIndicator3 mIndicator;
    LinearLayout pptLayout;
    int width;
    int height;
    String dirpath;
    private PdfDocument doc;
    private boolean[] array = new boolean[6];
    private static boolean isLoad = false;
    private int i;
    private int pageNum;
    private PfListAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_exam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        doc = new PdfDocument();

        pptLayout = findViewById(R.id.pptLayout);
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page, this);
        mPager.setAdapter(pagerAdapter);
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("onPageSelected", (position%num_page)+"번");
                pageNum = position%num_page;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager2.SCROLL_STATE_IDLE){
                    Log.v("hhd3",  "stop//");
                    //기기 화면 크기 구하기
                    width = pptLayout.getMeasuredWidth();
                    height = pptLayout.getMeasuredHeight();

                    Log.d("크기", width+"/ "+height);
                    Log.d("FragPage:hhd1", pageNum+"번");
//                    Log.d("hhd1", array[i-1]+"");

                    if(array[pageNum] == false) {
//                        Log.v("hhd2",i+"");
                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, pageNum).create();
                        PdfDocument.Page page = doc.startPage(pageInfo);

                        layoutToImage(pptLayout, page);

                        doc.finishPage(page);
                        array[pageNum] = true;
                    }

                    Log.d("hh4", Arrays.toString(array));

                }
            }
        });
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        /**
         * 이 부분 조정하여 처음 시작하는 이미지 설정.
         * 2000장 생성하였으니 현재위치 1002로 설정하여
         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
         */

        mPager.setCurrentItem(1002); //시작 지점
        mPager.setOffscreenPageLimit(6); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoad = true;
    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed();
        final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("PDF로 저장하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        //setNegativeButton
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(), PfList_Main.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"PDF 파일 생성!",Toast.LENGTH_SHORT).show();
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator + "myPDF.pdf");
                    Log.v("파일저장경로", file.getAbsolutePath());
                    try{
                        doc.writeTo(new FileOutputStream(file));
                        doc.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    imageToPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "취소 버튼이 눌렸습니다.".toString(),
                        Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "PDF 파일이 저장되지 않았습니다.".toString(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Ppt_tem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void layoutToImage (View view,PdfDocument.Page page) {
        LinearLayout linearLayout = view.findViewById(R.id.pptLayout);

        pptLayout.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));

        Bitmap bbmm = Bitmap.createBitmap(pptLayout.getMeasuredWidth(), pptLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasBitmap = new Canvas(bbmm);
        pptLayout.draw(canvasBitmap);

        Paint paint = new Paint();
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bbmm, 30, 50 ,paint);
    }

    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();

        } catch (Exception e) {

        }
    };

    @Override
    public void onPagechange(int i) {
        this.i = i ;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}




