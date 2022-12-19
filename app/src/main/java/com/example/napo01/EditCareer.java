package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.napo01.databinding.ActivityMainscreenBinding;
import com.google.android.material.navigation.NavigationView;

public class EditCareer extends AppCompatActivity implements View.OnClickListener {
    private TextView edt_int, edt_cer, edt_intn, edt_lang, edt_awds, edt_grad, edt_pfgo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcareer);


        edt_int = findViewById(R.id.edt_int);
        edt_int.setOnClickListener(this);
        edt_cer = findViewById(R.id.edt_cer);
        edt_cer.setOnClickListener(this);
        edt_intn = findViewById(R.id.edt_intn);
        edt_intn.setOnClickListener(this);
        edt_lang = findViewById(R.id.edt_lang);
        edt_lang.setOnClickListener(this);
        edt_awds = findViewById(R.id.edt_awds);
        edt_awds.setOnClickListener(this);
        edt_grad = findViewById(R.id.edt_grad);
        edt_grad.setOnClickListener(this);
        edt_pfgo = findViewById(R.id.edt_pfgo);
        edt_pfgo.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (viewId==R.id.edt_int){
            Intent intent = new Intent(getApplicationContext(), CareerIntroduce.class);
            startActivity(intent);
        }

        if(viewId==R.id.edt_cer){
            Intent intent = new Intent(getApplicationContext(), CareerCert_Main.class);
            startActivity(intent);
        }

        if(viewId==R.id.edt_intn){
            Intent intent = new Intent(getApplicationContext(), CareerIntern_Main.class);
            startActivity(intent);
        }

        if(viewId==R.id.edt_lang){
            Intent intent = new Intent(getApplicationContext(), CareerLang_Main.class);
            startActivity(intent);
        }

        if(viewId==R.id.edt_awds){
            Intent intent = new Intent(getApplicationContext(), CareerAwards_Main.class);
            startActivity(intent);
        }

        if(viewId==R.id.edt_grad){
            Intent intent = new Intent(getApplicationContext(), SettingMyPage.class);
            startActivity(intent);
        }
        if(viewId==R.id.edt_pfgo) {
            Intent intent = new Intent(getApplicationContext(), Ppt_tem.class);
            startActivity(intent);
        }
    }
}
