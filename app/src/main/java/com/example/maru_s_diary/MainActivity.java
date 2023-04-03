package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    private AlarmSettingFragment fragmentAlarmSetting = new AlarmSettingFragment();
    private SettingsFragment fragmentSetting = new SettingsFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //객체 정의
        SettingListener(); //리스너 등록

        // 알림 버튼을 눌렀을 때 실행
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_alarm) { // 알람 버튼 클릭 이벤트 처리
                    // 프래그먼트 이동
                    fragmentTransaction.replace(R.id.container, new AlarmFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });

    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mypage_item: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, fragmentAlarmSetting)
                            .commit();
                    return true;
                }
                case R.id.home_item: {
                    return true;
                }
                case R.id.setting_item: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, fragmentSetting)
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }



}