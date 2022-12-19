package com.example.napo01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.napo01.databinding.ActivityMainscreenBinding;
import com.google.android.material.navigation.NavigationView;



import java.util.List;


public class Mainscreen extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainscreenBinding binding;
    private ImageButton img_pfbtn;
    private TextView tv_mainInfo;
    private PermissionSupport permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_pf_add, R.id.nav_pf_list, R.id.nav_setting, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_mainscreen);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        permissionCheck();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id==R.id.nav_home){
                    Intent intent = new Intent(getApplicationContext(),New_Home.class);
                    startActivity(intent);
                } else if (id==R.id.nav_pf_add){
                    Intent intent = new Intent(getApplicationContext(), EditCareer.class);
                    startActivity(intent);
                } else if (id==R.id.nav_pf_list){
                    Intent intent = new Intent(getApplicationContext(), PfList_Main.class);
                    startActivity(intent);
                } else if (id==R.id.nav_logout){
                    Intent intent = new Intent(getApplicationContext(), Team_Login.class);
                    startActivity(intent);
                } else if (id==R.id.nav_setting){
                    Intent intent = new Intent(getApplicationContext(), SettingPage.class);
                    startActivity(intent);
                }
                return true;
            };
        });
        img_pfbtn = findViewById(R.id.img_pfbtn);
        img_pfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditCareer.class);
                startActivity(intent);
            }
        });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainscreen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_mainscreen);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    // 권한 체크
    private void permissionCheck() {

        // PermissionSupport.java 클래스 객체 생성
        permission = new PermissionSupport(this, this);

        // 권한 체크 후 리턴이 false로 들어오면
        if (!permission.checkPermission()){
            //권한 요청
            permission.requestPermission();
        }
    }

    // Request Permission에 대한 결과 값 받아와
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용 거부)
        if (!permission.permissionResult(requestCode, permissions, grantResults)) {
            // 다시 permission 요청
            permission.requestPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}