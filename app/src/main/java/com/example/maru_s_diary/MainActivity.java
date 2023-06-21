package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout home_ly;
    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    private SettingsFragment fragmentSetting = new SettingsFragment();
    private MypageCorrectionFragment fragmentMyPageCorrection = new MypageCorrectionFragment();
    private HomeFragment fragmentHome = new HomeFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView alarmBtn;
    private ImageView appbar_iv;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 값 가져오기
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = preferences.getInt("theme", 0); // 기본값은 0

        // Diary 프래그먼트로 SharedPreferences 인스턴스 전달
        Diary diaryFragment = new Diary();
        SharedPreferences sharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
        diaryFragment.setSharedPreferences(sharedPreferences);

        // 테마 설정
        if (theme == 0) {
            setTheme(R.style.CustomTheme); // 분홍색 테마
        } else if (theme == 1) {
            setTheme(R.style.CustomTheme2); // 하늘색 테마
        } else if (theme == 2) {
            setTheme(R.style.CustomTheme3); // 초록색 테마
        } else {
            setTheme(R.style.CustomTheme4); // 노란색 테마
        }

        setContentView(R.layout.activity_main);
        appbar_iv = findViewById(R.id.appbar_iv);
        changeTheme(theme);

        alarmBtn = findViewById(R.id.alarm_btn);
        // 알람 버튼 클릭 시
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "준비 중입니다.", Toast.LENGTH_SHORT).show();
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
        menu = bottomNavigationView.getMenu();
    }

    // 선택 리스너 등록
    private void setupListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    @Override
    public void onClick(View view) {

    }

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                break;
            case 1:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue)));
                break;
            case 2:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                break;
            case 3:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                break;
        }
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mypage_item: {
                    menuItem.setIcon(R.drawable.selected_mypage_icon);
                    menu.findItem(R.id.home_item).setIcon(R.drawable.unselected_home_icon);
                    menu.findItem(R.id.setting_item).setIcon(R.drawable.unselected_setting_icon);
                    fragmentManager.beginTransaction()
                            .replace(R.id.home_ly, fragmentMyPageCorrection)
                            .commit();
                    return true;
                }
                case R.id.home_item:{
                    menuItem.setIcon(R.drawable.selected_home_icon);
                    menu.findItem(R.id.mypage_item).setIcon(R.drawable.unselected_mypage_icon);
                    menu.findItem(R.id.setting_item).setIcon(R.drawable.unselected_setting_icon);
                    fragmentManager.beginTransaction()
                        .replace(R.id.home_ly, fragmentHome)
                        .commit();
                    return true;
                }
                case R.id.setting_item: {
                    menuItem.setIcon(R.drawable.selected_setting_icon);
                    menu.findItem(R.id.home_item).setIcon(R.drawable.unselected_home_icon);
                    menu.findItem(R.id.mypage_item).setIcon(R.drawable.unselected_mypage_icon);
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