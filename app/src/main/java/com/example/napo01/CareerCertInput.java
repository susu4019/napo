package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

//자격증 정보 입력 페이지
public class CareerCertInput extends AppCompatActivity {
    private RequestQueue queue;
    private StringRequest stringRequest;
    private TextView certNameInput, certInstInput, btn_cert;
    private EditText certDateInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careercert_lv_input);

        certNameInput = findViewById(R.id.certNameInput);
        certInstInput = findViewById(R.id.certInstInput);
        certDateInput = findViewById(R.id.certDateInput);
        btn_cert = findViewById(R.id.btn_lang_check);

        //자격증 검색
        certNameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CareerCertInput.this, CareerCertSearch.class), 1234);
            }
        });

        //자격증 데이터 서버로 전송
        btn_cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
                Intent intent = new Intent(getApplicationContext(), CareerCert_Main.class);
                startActivity(intent);
            }
        });
    }

    //자격증 데이터 서버 전송
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/cert";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
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
                String ser = certNameInput.getText().toString();
                String inst = certInstInput.getText().toString();
                String date = certDateInput.getText().toString();

                params.put("ser",ser);
                params.put("inst",inst);
                params.put("date",date);

                return params;
            }
        };
        String TAG = "cert check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    //자격증 발급 일자 달력 띄우기
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment(CareerCertInput.this, "CareerCert");
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "년 " + month_string + "월 " + day_string + "일");
        certDateInput.setText(dateMessage);
    }

    //자격증명 검색 결과 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234){
            if(resultCode == RESULT_OK){
                String choice = data.getStringExtra("choice");
                Log.d("확인", choice);
                certNameInput.setText(choice);
            }
        }
    }
}