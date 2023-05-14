package com.example.maru_s_diary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PwChangeFragment extends AppCompatActivity {
    ImageButton backBtn;
    Button PWchangeBtn;
    EditText currentPW, newPW, newPWcheck;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);

        backBtn = findViewById(R.id.backBtn);
        PWchangeBtn = findViewById(R.id.btn_check);
        currentPW = findViewById(R.id.et_now_pw);
        newPW = findViewById(R.id.et_pw);
        newPWcheck = findViewById(R.id.et_pw2);
        mAuth = FirebaseAuth.getInstance();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        PWchangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePW();
            }
        });
    }

    private void changePW() {
        String currentPassword = currentPW.getText().toString().trim();
        final String newPassword = newPW.getText().toString().trim();
        String newPasswordCheck = newPWcheck.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Verify current password
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // 비밀번호가 일치하는지 확인
                            if (newPassword.isEmpty() || newPasswordCheck.isEmpty()) {
                                Toast.makeText(PwChangeFragment.this, "새로운 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }else if (newPassword.equals(newPasswordCheck)) {
                                // Update password
                                user.updatePassword(newPassword).addOnCompleteListener(passwordUpdateTask -> {
                                            if (passwordUpdateTask.isSuccessful()) {
                                                Toast.makeText(PwChangeFragment.this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(PwChangeFragment.this, "비밀번호 변경에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(PwChangeFragment.this, "새로운 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PwChangeFragment.this, "현재 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
