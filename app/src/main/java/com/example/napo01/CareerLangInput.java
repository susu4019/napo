package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class CareerLangInput extends AppCompatActivity {
    private ListView careerlang_list;
    private CareerLangAdapter careerLangAdapter = new CareerLangAdapter();
    private TextView lang_test, btn_lang_check;
    private EditText lang_kind,lang_score, lang_date;
    private RequestQueue queue;
    private StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerlang_lv_input);
        careerlang_list = findViewById(R.id.careerLang_List);
        lang_kind = findViewById(R.id.lang_kind);
        lang_test = findViewById(R.id.lang_test);
        lang_score = findViewById(R.id.lang_score);
        lang_date = findViewById(R.id.lang_date);
        btn_lang_check = findViewById(R.id.btn_lang_check);


        //언어 검색
        lang_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CareerLangInput.this,CareerLangSearch.class), 1234);
            }
        });

        //언어 데이터 서버로 전송
        btn_lang_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
                Intent intent = new Intent(getApplicationContext(), CareerLang_Main.class);
                startActivity(intent);
            }
        });
    }

        //언어 데이터 서버 전송
        public void sendRequest(){
            queue = Volley.newRequestQueue(this);
            //서버 요청 주소
            String url = "http://192.168.219.149:5001/lang";

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
                    String kind = lang_kind.getText().toString();
                    String test = lang_test.getText().toString();
                    String score = lang_score.getText().toString();
                    String date = lang_date.getText().toString();

                    params.put("kind",kind);
                    params.put("test",test);
                    params.put("score",score);
                    params.put("date",date);

                    return params;
                }
            };
            String TAG = "lang check";
            stringRequest.setTag(TAG);
            queue.add(stringRequest);
        }

        //언어 검색 결과 받아오기
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1234){
                if(resultCode == RESULT_OK){
                    String choice = data.getStringExtra("choice");
                    Log.d("확인", choice);
                    lang_test.setText(choice);
                }
            }
        }

        //언어 발급 일자 달력 띄우기
        public void showDatePicker(View view) {
            DialogFragment newFragment = new DatePickerFragment(this, "CareerLang");
            newFragment.show(getSupportFragmentManager(),"datePicker");
        }
        public void processDatePickerResult(int year, int month, int day){
            String month_string = Integer.toString(month+1);
            String day_string = Integer.toString(day);
            String year_string = Integer.toString(year);
            String dateMessage = (year_string + "년 " + month_string + "월 " + day_string + "일");

            TextView lang_date = findViewById(R.id.lang_date);
            lang_date.setText(dateMessage);
        }
}


