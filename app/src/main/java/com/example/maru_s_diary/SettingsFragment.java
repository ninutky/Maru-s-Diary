package com.example.maru_s_diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    TextView prefer_delaccount_tvbtn;
    LinearLayout prefer_logout_llbtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        prefer_delaccount_tvbtn = v.findViewById(R.id.prefer_delaccount_tvbtn);
        prefer_logout_llbtn = v.findViewById(R.id.prefer_logout_llbtn);
        prefer_delaccount_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        prefer_logout_llbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }


}
