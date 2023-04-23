package com.example.maru_s_diary;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmSettingActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Switch dndModeSwitch;
    private LinearLayout dndModeLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting); // 레이아웃 파일 설정
        backBtn = findViewById(R.id.backBtn);
        dndModeSwitch = findViewById(R.id.dnd_mode_switch);
        dndModeLy = findViewById(R.id.dnd_mode_ly);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 방해 금지 모드 스위치 이벤트
        dndModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dndModeLy.setVisibility(View.VISIBLE); // 뷰 보이기
                } else {
                    dndModeLy.setVisibility(View.GONE); // 뷰 숨기기
                }
            }
        });

    }
}
