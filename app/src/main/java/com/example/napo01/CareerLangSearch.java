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

public class CareerLangSearch extends AppCompatActivity {

    EditText langSearch;
    ListView searchList;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerlang_search);

        langSearch = findViewById(R.id.cert_search);
        searchList = findViewById(R.id.cert_searchList);
        arrayList = new ArrayList<>();

        arrayList.add("DALE");
        arrayList.add("DALF");
        arrayList.add("DELF");
        arrayList.add("FELX");
        arrayList.add("HSK");
        arrayList.add("IELTS");
        arrayList.add("JLPT");
        arrayList.add("JPT");
        arrayList.add("OPIC");
        arrayList.add("TEPS");
        arrayList.add("TFI");
        arrayList.add("TOEIC");
        arrayList.add("TOEIC Bridge");
        arrayList.add("TOEIC Speaking");
        arrayList.add("TOEFL");
        arrayList.add("TORFL");
        arrayList.add("TSC");

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        searchList.setAdapter(adapter);

        langSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = langSearch.getText().toString();
                ArrayList<String> search_arrayList = new ArrayList<String>();
                for(int ii = 0; ii < arrayList.size(); ii++){
                    if(arrayList.get(ii).startsWith(value)){
                        search_arrayList.add(arrayList.get(ii));
                    }
                }
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,search_arrayList);
                searchList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String value = (String)adapterView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("choice",  value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
