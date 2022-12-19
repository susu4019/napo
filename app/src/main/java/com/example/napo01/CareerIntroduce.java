package com.example.napo01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class CareerIntroduce extends AppCompatActivity {
    TextView tv_intro;
    EditText edt_intro;
    Button intro_check;
    private RequestQueue queue;
    private StringRequest stringRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerintroduce);
        tv_intro = findViewById(R.id.tv_intro);
        edt_intro = findViewById(R.id.edt_intro);
        intro_check = findViewById(R.id.intro_check);

        tv_intro.setVisibility(View.INVISIBLE);

        intro_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_intro.setVisibility(View.VISIBLE);
                edt_intro.setVisibility(View.INVISIBLE);
                String text = edt_intro.getText().toString();
                tv_intro.setText(text);
                sendRequest();
            }
        });
    }

    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/intro";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);
                String introduce = response;
                if (introduce == null) {
                    tv_intro.setVisibility(View.INVISIBLE);
                    edt_intro.setVisibility(View.VISIBLE);
                } else {
                    edt_intro.setVisibility(View.INVISIBLE);
                    tv_intro.setVisibility(View.VISIBLE);
                    tv_intro.setText(introduce);
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

            //보낼 데이터 저장 - CareerIntern_Main에서는 보낼 데이터 없음
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String intro = edt_intro.getText().toString();
                params.put("intro",intro);
                return params;
            }
        };
        String TAG = "intern check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
}
