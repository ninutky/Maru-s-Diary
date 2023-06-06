package com.example.maru_s_diary;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmSettingActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting); // 레이아웃 파일 설정

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

}
