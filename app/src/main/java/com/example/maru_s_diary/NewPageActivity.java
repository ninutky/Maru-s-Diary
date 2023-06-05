package com.example.maru_s_diary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewPageActivity extends AppCompatActivity {
    //private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mTitle,mContents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_diary);
    }
}
