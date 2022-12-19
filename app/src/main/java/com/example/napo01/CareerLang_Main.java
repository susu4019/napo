package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CareerLang_Main extends AppCompatActivity {
    private ListView careerLang_List;
    private CareerLangAdapter careerLangAdapter = new CareerLangAdapter();
    private TextView btn_langPlus;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerlang);
        careerLang_List = findViewById(R.id.careerLang_List);
        btn_langPlus = findViewById(R.id.btn_langPlus);

        //언어 정보 출력할 Adapter
        careerLang_List.setAdapter(careerLangAdapter);

        //언어 정보 받아올 서버
        sendRequest();

        //언어 정보 입력하러 가기 Intent
        btn_langPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CareerLangInput.class);
                startActivity(intent);
          }
        });

    }
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/lang_result";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            //언어 정보 Adapter에 출력
            @Override
            public void onResponse(String response) {
                String[] langs = response.split("/");
                String[][] lang_list = new String [langs.length][4];
                for(int i =0; i<lang_list.length; i++){
                    if (langs ==null) {
                        careerLang_List.setAdapter(careerLangAdapter);
                        careerLangAdapter.addItems("","","","");
                        careerLangAdapter.notifyDataSetChanged();
                    }
                    else {
                        String [] values = langs[i].split(",");
                        lang_list[i][0] = values[0];
                        lang_list[i][1] = values[1];
                        lang_list[i][2] = values[2];
                        lang_list[i][3] = values[3];
                        careerLang_List.setAdapter(careerLangAdapter);
                        careerLangAdapter.addItems(lang_list[i][0] = values[0],lang_list[i][1] = values[1],
                                                    lang_list[i][2] = values[2],lang_list[i][3] = values[3]);
                        careerLangAdapter.notifyDataSetChanged();
                        Log.v("lang", lang_list[i][0] = values[0]);
                        Log.v("lang", lang_list[i][1] = values[1]);
                        Log.v("lang", lang_list[i][2] = values[2]);
                        Log.v("lang", lang_list[i][3] = values[3]);
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

            //보낼 데이터 저장 - CareerLang_Main에서는 보낼 데이터 없음
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        String TAG = "lang check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }



}
