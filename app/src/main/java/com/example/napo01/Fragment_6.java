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

public class Fragment_6 extends Fragment {
    private PageChangeListener listener;
    private TextView textView3, f_intern_1,f_intern_2,f_intern_3,
                    f_intern_act_1,f_intern_act_2,f_intern_act_3;
    private RequestQueue queue;
    private StringRequest stringRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag6, container, false);
        textView3 = rootView.findViewById(R.id.textView3);
        f_intern_1 = rootView.findViewById(R.id.f_intern_1);
        f_intern_2 = rootView.findViewById(R.id.f_intern_2);
        f_intern_3 = rootView.findViewById(R.id.f_intern_3);
        f_intern_act_1 = rootView.findViewById(R.id.f_intern_act_1);
        f_intern_act_2 = rootView.findViewById(R.id.f_intern_act_2);
        f_intern_act_3 = rootView.findViewById(R.id.f_intern_act_3);
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
        listener.onPagechange(6);
    }
    public void sendRequest(){
        queue = Volley.newRequestQueue(getContext());
        //서버 요청 주소
        String url = "http://192.168.219.149:5001/intern_result";

        //요청 문자열 저장
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //응답 데이터 받아오는 곳
            @Override
            public void onResponse(String response) {
                String[] interns = response.split("/");
                try {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            String[] values = interns[i].split(",");
                            f_intern_1.setText(values[0]);
                            f_intern_act_1.setText(values[2]);
                        }else if (i == 1) {
                            String[] values = interns[i].split(",");
                            f_intern_2.setText(values[0]);
                            f_intern_act_2.setText(values[2]);
                        }else if (i == 2) {
                            String[] values = interns[i].split(",");
                            f_intern_3.setText(values[0]);
                            f_intern_act_3.setText(values[2]);
                        }
                    }
                }catch (Exception e){
                    System.out.println("자료없음");
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
                return params;
            }
        };
        String TAG = "intern check";
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
}
