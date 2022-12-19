package com.example.napo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingPage extends AppCompatActivity {
    TextView goMyPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingpage);
        goMyPage = findViewById(R.id.goMyPage);

        goMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingMyPage.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Mainscreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
