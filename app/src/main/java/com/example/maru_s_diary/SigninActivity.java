package com.example.maru_s_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;     // 파이어 베이스 인증
    private DatabaseReference mDatabase;    // 실시간 데이터 베이스
    private EditText mEtId, mEtPwd;
    private Button mBtnRegister;
    TextView tvbtn_signup, tvbtn_pwfind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        tvbtn_signup = findViewById(R.id.tvbtn_signup); // 회원가입
        tvbtn_pwfind = findViewById(R.id.tvbtn_pwfind); // 비밀번호 찾기
        mEtId = findViewById(R.id.et_id);
        mEtPwd = findViewById(R.id.et_pw);
        mBtnRegister = findViewById(R.id.btn_signin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvbtn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}