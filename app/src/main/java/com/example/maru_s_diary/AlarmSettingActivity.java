package com.example.maru_s_diary;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmSettingActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private ImageButton backBtn;
    private ImageView appbar_iv;
    private Switch allSwt, commSwt, likeSwt, nightSwt;
    private LinearLayout nightTextLly;
    private ImageView nightIv;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 값 가져오기
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0); // 기본값은 0
        preferences = getPreferences(Context.MODE_PRIVATE);
        int themeColor = preferences.getInt("themeColor", R.color.pink_50); // 기본 테마 분홍색

        setContentView(R.layout.activity_alarm_setting); // 레이아웃 파일 설정

        appbar_iv = findViewById(R.id.appbar_iv);
        allSwt = findViewById(R.id.all_swt);
        commSwt = findViewById(R.id.comm_swt);
        likeSwt = findViewById(R.id.like_swt);
        nightSwt = findViewById(R.id.night_swt);
        nightIv = findViewById(R.id.night_iv);
        nightTextLly = findViewById(R.id.night_text_lly);

        appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        allSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        commSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        likeSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        nightSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        nightIv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        changeTheme(theme);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 방해금지 모드 설정 함수 호출 (HH:mm 형식으로 전달)
        setDowntime("22:00", "08:00");

        // 뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nightSwt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    nightTextLly.setVisibility(View.VISIBLE);
                } else {
                    nightTextLly.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setDowntime(String startTime, String endTime) {
        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");

        // 시작 시간 설정
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeParts[0]));
        calendarStart.set(Calendar.MINUTE, Integer.parseInt(startTimeParts[1]));
        calendarStart.set(Calendar.SECOND, 0);

        // 종료 시간 설정
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeParts[0]));
        calendarEnd.set(Calendar.MINUTE, Integer.parseInt(endTimeParts[1]));
        calendarEnd.set(Calendar.SECOND, 0);

        // 현재 시간 확인
        Calendar currentTime = Calendar.getInstance();

        // 현재 시간이 방해금지 모드 시간 범위에 속하는지 확인
        if (currentTime.after(calendarStart) && currentTime.before(calendarEnd)) {
            // 현재 시간이 방해금지 모드 시간 범위에 속하는 경우
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
            } else {
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                allSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                commSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                likeSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                nightSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                nightIv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                break;
            case 1:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue)));
                allSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                commSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                likeSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                nightSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                nightIv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                break;
            case 2:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                allSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                commSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                likeSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                nightSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                nightIv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                break;
            case 3:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                allSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                commSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                likeSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                nightSwt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                nightIv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                break;
        }
    }

}
