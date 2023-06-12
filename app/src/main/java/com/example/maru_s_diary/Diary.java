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
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Diary extends Fragment {
    private ImageButton backBtn;
    private ImageButton reportBtn;
    private Dialog reportdlg;

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


        return v;
    }

    public void showReportDialog(){
        reportdlg.show(); // 다이얼로그 띄우기
        reportdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}