package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout home_ly;
    private BottomNavigationView bottomNavigationView;
    private SettingsFragment fragmentSetting = new SettingsFragment();
    private MypageCorrectionFragment fragmentMyPageCorrection = new MypageCorrectionFragment();
    private HomeFragment fragmentHome = new HomeFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView alarmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmBtn = findViewById(R.id.alarm_btn);
        // 알람 버튼 클릭 시
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        init(); // 객체 정의
        setupListeners(); // 리스너 등록

        fragmentManager.beginTransaction()
                .replace(R.id.home_ly, fragmentHome)
                .commit();

    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        home_ly.setLayoutParams(layoutParams);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
    }

    // 선택 리스너 등록
    private void setupListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    @Override
    public void onClick(View view) {

    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mypage_item: {
                    fragmentManager.beginTransaction()
                            .replace(R.id.home_ly, fragmentMyPageCorrection)
                            .commit();
                    return true;
                }
                case R.id.home_item: {
                    fragmentManager.beginTransaction()
                        .replace(R.id.home_ly, fragmentHome)
                        .commit();
                    return true;
                }
                case R.id.setting_item: {
                    fragmentManager.beginTransaction()
                            .replace(R.id.home_ly, fragmentSetting)
                            .commit();
                    return true;
                }
            }
            return false;
        }
    }



}