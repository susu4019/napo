package com.example.napo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        public class Team_Login extends AppCompatActivity {
            private EditText edt_login_id, edt_login_pw;
            private TextView btn_join, btn_login, btn_idpw_find;

            private RequestQueue queue;
            private StringRequest stringRequest;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_team_login);

                edt_login_id = findViewById(R.id.edt_login_Id);
                edt_login_pw = findViewById(R.id.edt_login_pw);
                btn_join = findViewById(R.id.btn_join);
                btn_login = findViewById(R.id.btn_login);
                btn_idpw_find = findViewById(R.id.btn_idpw_find);

                btn_join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), join.class);
                        startActivity(intent);
                    }
                });

                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        sendRequest();

                    }
                });

            }
            public void sendRequest(){
                queue = Volley.newRequestQueue(this);
                String url = "http://192.168.21.247:5001/login";
                stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("resultValue",response);

                        if (response.equals("Y")) {
                            Intent intent = new Intent(getApplicationContext(), Mainscreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }

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

                        String id = edt_login_id.getText().toString();
                        String pw = edt_login_pw.getText().toString();
                        params.put("id",id);
                        params.put("pw",pw);
                        return params;

                    }
                };

                String TAG = "duud";
                stringRequest.setTag(TAG);
                queue.add(stringRequest);

            }
}