package com.example.maru_s_diary;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    TextView prefer_delaccount_tvbtn, prefer_changepw_tvbtn;
    LinearLayout prefer_logout_llbtn;
    Dialog loutdlg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        prefer_delaccount_tvbtn = v.findViewById(R.id.prefer_delaccount_tvbtn);
        prefer_logout_llbtn = v.findViewById(R.id.prefer_logout_llbtn);
        prefer_changepw_tvbtn = v.findViewById(R.id.prefer_changepw_tvbtn);

        loutdlg = new Dialog(getActivity());
        loutdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loutdlg.setContentView(R.layout.fragment_logout_dialog);

        prefer_delaccount_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        prefer_changepw_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PwChangeFragment.class);
                startActivity(intent);
            }
        });

        prefer_logout_llbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
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
