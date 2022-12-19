package com.example.napo01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SettingMyPage extends AppCompatActivity {
    private TextView btn_set;
    private EditText set_name, set_email, set_grad;
    private RequestQueue queue;
    private StringRequest stringRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingmypage);

        set_name = findViewById(R.id.set_name);
        set_email = findViewById(R.id.set_email);
        set_grad = findViewById(R.id.set_grad);


    }

    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/user";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue", response);

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

            //보낼 데이터 저장
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String name = set_name.getText().toString();
                String email = set_email.getText().toString();
                String grad = set_grad.getText().toString();
                params.put("name",name);
                params.put("email",email);
                params.put("grad",grad);
                return params;
            }
        };
        String TAG = "user check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

}
