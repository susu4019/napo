package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// 자격증 정보 검색 페이지
public class CareerCertSearch extends AppCompatActivity {

    EditText cert_search;
    ListView cert_searchList;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careercert_search);

        cert_search = findViewById(R.id.cert_search);
        cert_searchList = findViewById(R.id.cert_searchList);

        arrayList = new ArrayList<>();

        arrayList.add("건축기사");
        arrayList.add("데이터분석 전문가");
        arrayList.add("데이터분석 준전문가");
        arrayList.add("데이터아키텍처 전문가");
        arrayList.add("데이터아키텍처 준전문가");
        arrayList.add("무역영어");
        arrayList.add("물류관리사");
        arrayList.add("비서");
        arrayList.add("빅데이터분석기사");
        arrayList.add("사무자동차산업기사");
        arrayList.add("워드프로세서");
        arrayList.add("유통관리사");
        arrayList.add("임베디드기사");
        arrayList.add("재경관리사");
        arrayList.add("전기공사기사");
        arrayList.add("전기기사");
        arrayList.add("전산세무회계");
        arrayList.add("전산세무 1급");
        arrayList.add("전산세무 2급");
        arrayList.add("전산회계운용사");
        arrayList.add("전산회계 1급");
        arrayList.add("전산회계 2급");
        arrayList.add("전자상거래관리사");
        arrayList.add("전자상거래운용사");
        arrayList.add("정보관리기술사");
        arrayList.add("정보처리기사");
        arrayList.add("정보처리산업기사");
        arrayList.add("정보통신기사");
        arrayList.add("정보통신기술사");
        arrayList.add("컴퓨터 활용능력 1급");
        arrayList.add("컴퓨터 활용능력 2급");
        arrayList.add("토목기사");
        arrayList.add("한국사능력검정시험");
        arrayList.add("한글속기");
        arrayList.add("한국어능력시험");
        arrayList.add("SQL 개발자");
        arrayList.add("SQL 전문가");
        arrayList.add("TAT");
        arrayList.add("FAT");

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        cert_searchList.setAdapter(adapter);

        cert_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = cert_search.getText().toString();
                ArrayList<String> search_arrayList = new ArrayList<String>();
                for(int ii = 0; ii < arrayList.size(); ii++){
                    if(arrayList.get(ii).startsWith(value)){
                        search_arrayList.add(arrayList.get(ii));
                    }
                }
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,search_arrayList);
                cert_searchList.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cert_searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String)adapterView.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.putExtra("choice", value);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
