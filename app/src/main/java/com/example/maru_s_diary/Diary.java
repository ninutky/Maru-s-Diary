package com.example.maru_s_diary;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Diary extends Fragment {
    private ImageButton backBtn;
    private ImageButton reportBtn;
    private Dialog reportdlg;
    private LinearLayout diaryLly;
    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary, container, false);

        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        reportBtn = v.findViewById(R.id.report);
        reportdlg = new Dialog(getActivity());
        reportdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reportdlg.setContentView(R.layout.fragment_report_dialog);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReportDialog();
            }
        });
        reportdlg.findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportdlg.dismiss();
            }
        });
        radioGroup = reportdlg.findViewById(R.id.report_group);
        reportdlg.findViewById(R.id.report_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    Toast.makeText(getContext(), "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    reportdlg.dismiss();
                } else {
                    Toast.makeText(getContext(), "신고 사유를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        diaryLly = v.findViewById(R.id.diary_lly);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0);
        changeTheme(theme);

        return v;
    }

    public void showReportDialog(){
        reportdlg.show(); // 다이얼로그 띄우기
        reportdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                break;
            case 1:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                break;
            case 2:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                break;
            case 3:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                break;
        }
    }

}