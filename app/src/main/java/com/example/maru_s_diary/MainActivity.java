package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); // 객체 정의
        setupListeners(); // 리스너 등록
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

    // 알림 버튼을 눌렀을 때 실행
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_alarm) { // 알람 버튼 클릭 이벤트 처리
                    // 프래그먼트 이동
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new AlarmFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
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