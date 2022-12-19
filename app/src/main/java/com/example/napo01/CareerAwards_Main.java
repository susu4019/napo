package com.example.napo01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CareerAwards_Main extends AppCompatActivity {
    private ListView careerAwardsList;
    private CareerAwardsAdapter careerawardsAdapter = new CareerAwardsAdapter();
    private TextView btn_awards_plus;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerawards);
        careerAwardsList = findViewById(R.id.careerAwardsList);
        btn_awards_plus = findViewById(R.id.btn_awards_plus);
        //수상내역 정보 출력할 Adapter
        careerAwardsList.setAdapter(careerawardsAdapter);

        //수상내역 정보 받아올 서버
        sendRequest();

        //수상정보 입력하러 가기 Intent
        btn_awards_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CareerAwardsInput.class);
                startActivity(intent);
            }
        });
    }
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/awards_result";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            //수상정보 Adapter에 출력
            @Override
            public void onResponse(String response) {
                String[] awards = response.split("/");
                String[][] awards_list = new String [awards.length][3];
                for(int i =0; i<awards_list.length; i++){
                    if (awards ==null) {
                        careerAwardsList.setAdapter(careerawardsAdapter);
                        careerawardsAdapter.addItems("","","");
                        careerawardsAdapter.notifyDataSetChanged();
                    }
                    else {
                        String [] values = awards[i].split(",");
                        awards_list[i][0] = values[0];
                        awards_list[i][1] = values[1];
                        awards_list[i][2] = values[2];
                        careerAwardsList.setAdapter(careerawardsAdapter);
                        careerawardsAdapter.addItems(awards_list[i][0] = values[0],awards_list[i][1] = values[1],awards_list[i][2] = values[2]);
                        careerawardsAdapter.notifyDataSetChanged();
                        Log.v("awards", awards_list[i][0] = values[0]);
                        Log.v("awards", awards_list[i][1] = values[1]);
                        Log.v("awards", awards_list[i][2] = values[2]);
                    }
                }
            }
        }, new Response.ErrorListener() {
            //에러 발생시
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            //response를 UTF8로 변경
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {

                    return Response.error(new ParseError(e));
                } catch (Exception e) {

                    return Response.error(new ParseError(e));
                }
            }

            //보낼 데이터 저장 - CareerAwards_Main에서는 보낼 데이터 없음
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        String TAG = "award check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

}