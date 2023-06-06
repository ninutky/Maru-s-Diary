package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    Button mLoginBtn;
    TextView mSignup, findId;
    EditText mId, mPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();

        // 버튼 등록하기
        mLoginBtn = findViewById(R.id.btn_signin);
        mSignup = findViewById(R.id.tvbtn_signup);
        mId = findViewById(R.id.et_id);
        mPassword = findViewById(R.id.et_pw);
        findId = findViewById(R.id.tvbtn_idfind);

        // 회원가입을 누르면
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent 함수를 통해 Signup 액티비티 함수 호출
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });

        // 로그인 버튼을 누르면
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mId.getText().toString().trim();
                String pwd = mPassword.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(id, pwd)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(SigninActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SigninActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

//        findId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SigninActivity.this, FindId.class));
//            }
//        });

    }
}