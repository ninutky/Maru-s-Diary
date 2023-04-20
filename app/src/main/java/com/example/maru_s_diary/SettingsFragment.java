package com.example.maru_s_diary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    TextView delaccount_tvbtn, changepw_tvbtn, alram_tvbtn;
    LinearLayout logout_llbtn;
    Dialog loutdlg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        delaccount_tvbtn = v.findViewById(R.id.set_delaccount_tvbtn);
        logout_llbtn = v.findViewById(R.id.set_logout_llbtn);
        changepw_tvbtn = v.findViewById(R.id.set_changepw_tvbtn);
        alram_tvbtn = v.findViewById(R.id.set_alram_tvbtn);

        loutdlg = new Dialog(getActivity());
        loutdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loutdlg.setContentView(R.layout.fragment_logout_dialog);

        delaccount_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        changepw_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PwChangeFragment.class);
                startActivity(intent);
            }
        });

        logout_llbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        alram_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
                startActivity(intent);
            }
        });


        return v;


    }

    public void showDialog(){
        loutdlg.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // dialog 라운드 끝 하얀 배경 삭제
        loutdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 아니오 버튼
        Button noBtn = loutdlg.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                loutdlg.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        loutdlg.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                // 로그아웃
            }
        });
    }

}
