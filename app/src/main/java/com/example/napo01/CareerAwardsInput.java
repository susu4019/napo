package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class CareerAwardsInput extends AppCompatActivity {

    private EditText awardsName, awardsInst, awardsDate;
    private Button btn_awards;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerawards_lv_input);
        awardsName = findViewById(R.id.internName);
        awardsInst = findViewById(R.id.awardsInst);
        awardsDate = findViewById(R.id.awardsDate);
        btn_awards = findViewById(R.id.btn_intern_plus);

        btn_awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
                Intent intent = new Intent(getApplicationContext(), CareerAwards_Main.class);
                startActivity(intent);
            }
        });
    }

    //자격증 데이터 서버 전송
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/awards";

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
                String name = awardsName.getText().toString();
                String inst = awardsInst.getText().toString();
                String date = awardsDate.getText().toString();

                params.put("name",name);
                params.put("inst",inst);
                params.put("date",date);

                return params;
            }
        };
        String TAG = "awards check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }


    //수상 발급 일자 달력 띄우기
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment(CareerAwardsInput.this, "CareerAwards");
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "년 " + month_string + "월 " + day_string + "일");
        awardsDate.setText(dateMessage);
    }
}
