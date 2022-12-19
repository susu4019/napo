package com.example.napo01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
import com.example.napo01.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Fragment_4 extends Fragment {
    private PageChangeListener listener;
    private TextView f_lang_1, f_lang_2, f_lang_3,
                    f_lang_s_1,f_lang_s_2,f_lang_s_3;
    private RequestQueue queue;
    private StringRequest stringRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag4, container, false);
        f_lang_1 = rootView.findViewById(R.id.f_lang_1);
        f_lang_2 = rootView.findViewById(R.id.f_lang_2);
        f_lang_3 = rootView.findViewById(R.id.f_lang_3);
        f_lang_s_1 = rootView.findViewById(R.id.f_lang_s_1);
        f_lang_s_2 = rootView.findViewById(R.id.f_lang_s_2);
        f_lang_s_3 = rootView.findViewById(R.id.f_lang_s_3);
        sendRequest();
        return rootView;
    }

    public void setListener(PageChangeListener listener) {

        this.listener = listener;
    }
    @Override
    public void onResume() {
        super.onResume();
        this.listener = (PageChangeListener) getArguments().getSerializable("listener");
        listener.onPagechange(4);
    }

    public void sendRequest(){
        queue = Volley.newRequestQueue(getContext());
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/lang_result";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            @Override
            public void onResponse(String response) {
                String[] langs = response.split("/");
                try {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            String[] values = langs[i].split(",");
                            f_lang_1.setText(values[1]);
                            f_lang_s_1.setText(values[2]);
                        }
                        if (i == 1) {
                            String[] values = langs[i].split(",");
                            f_lang_2.setText(values[1]);
                            f_lang_s_2.setText(values[2]);
                        }
                        if (i == 2) {
                            String[] values = langs[i].split(",");
                            f_lang_3.setText(values[1]);
                            f_lang_s_3.setText(values[2]);
                        }
                    }
                }catch (Exception e){
                    System.out.println("error");
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
            //보낼 데이터 저장 - 보낼 데이터 없음
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
