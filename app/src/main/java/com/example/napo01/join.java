package com.example.napo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class join extends AppCompatActivity {
    private EditText edt_member_id, edt_member_pw, edt_member_pwCheck,edt_member_name,edt_member_phone;
    private CheckBox checkBox_member_agr;
    private TextView btn_member_join;

    private RequestQueue queue;
    private StringRequest stringRequest;

    private String id;
    private String pw;
    private String pwCheck;
    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edt_member_id= findViewById(R.id.edt_member_id);
        edt_member_pw = findViewById(R.id.edt_member_pw);
        edt_member_pwCheck = findViewById(R.id.edt_member_pwCheck);
        edt_member_name = findViewById(R.id.edt_member_name);
        edt_member_phone = findViewById(R.id.edt_member_phone);

        checkBox_member_agr = findViewById(R.id.checkBox_member_agr);

        btn_member_join= findViewById(R.id.btn_member_join);
        btn_member_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = edt_member_id.getText().toString();
                pw = edt_member_pw.getText().toString();
                pwCheck = edt_member_pwCheck.getText().toString();
                name = edt_member_name.getText().toString();
                phone = edt_member_phone.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Team_Login.class);

                if (!pw.equals(pwCheck)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }if (!checkBox_member_agr.isChecked()) {
                    Toast.makeText(getApplicationContext(), "동의하여주십시오.", Toast.LENGTH_SHORT).show();
                }

                Log.v("ahaha", pw);

                sendRequest();


                if(pw.equals(pwCheck)&&checkBox_member_agr.isChecked()){
                    startActivity(intent);
                }
//                String value = edt_member_name.getText().toString() + edt_member_phone.getText().toString();


            }
        });


    }
    public void sendRequest(){

        queue = Volley.newRequestQueue(this);
        String url = "http://192.168.21.247:5001/join";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("id",id);
                params.put("pw",pw);
                params.put("name",name);
                params.put("phone",phone);

                return params;

            }
        };

        String TAG = "duud";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
}