package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CareerCert_Main extends AppCompatActivity {
    private ListView careercert_List;
    private CareerCertAdapter careerCertAdapter = new CareerCertAdapter();
    private TextView btn_certPlus;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careercert);
        careercert_List = findViewById(R.id.careercert_List);
        btn_certPlus = findViewById(R.id.btn_certPlus);

        //자격증 정보 출력할 Adapter
        careercert_List.setAdapter(careerCertAdapter);

        //자격증 정보 받아올 서버
        sendRequest();

        //자격증 정보 입력하러 가기 Intent
        btn_certPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CareerCertInput.class);
                startActivity(intent);
            }
        });
    }
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/cert_result";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            //자격증 정보 Adapter에 출력
            @Override
            public void onResponse(String response) {
                String[] certs = response.split("/");
                String[][] cert_list = new String [certs.length][3];
                for(int i =0; i<cert_list.length; i++){
                    if (certs ==null) {
                        careercert_List.setAdapter(careerCertAdapter);
                        careerCertAdapter.addItems("","","");
                        careerCertAdapter.notifyDataSetChanged();
                    }
                    else {
                        String [] values = certs[i].split(",");
                        cert_list[i][0] = values[0];
                        cert_list[i][1] = values[1];
                        cert_list[i][2] = values[2];
                        careercert_List.setAdapter(careerCertAdapter);
                        careerCertAdapter.addItems(cert_list[i][0] = values[0],cert_list[i][1] = values[1],cert_list[i][2] = values[2]);
                        careerCertAdapter.notifyDataSetChanged();
                        Log.v("cert", cert_list[i][0] = values[0]);
                        Log.v("cert", cert_list[i][1] = values[1]);
                        Log.v("cert", cert_list[i][2] = values[2]);
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

            //보낼 데이터 저장 - CareerCert_Main에서는 보낼 데이터 없음
           @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        String TAG = "cert check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
}
