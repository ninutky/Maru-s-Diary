package com.example.maru_s_diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PwChangeActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Button PWchangeBtn;
    private EditText currentPW, newPW, newPWcheck;
    private ImageView appbar_iv;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;
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

        // 값 가져오기
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0); // 기본값은 0
        preferences = getPreferences(Context.MODE_PRIVATE);
        int themeColor = preferences.getInt("themeColor", R.color.pink_50); // 기본 테마 분홍색

        setContentView(R.layout.activity_delete_account);

        appbar_iv = findViewById(R.id.appbar_iv);
        appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        changeTheme(theme);

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
                                Toast.makeText(PwChangeActivity.this, "새로운 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }else if (newPassword.equals(newPasswordCheck)) {
                                // Update password
                                user.updatePassword(newPassword).addOnCompleteListener(passwordUpdateTask -> {
                                            if (passwordUpdateTask.isSuccessful()) {
                                                Toast.makeText(PwChangeActivity.this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(PwChangeActivity.this, "비밀번호 변경에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(PwChangeActivity.this, "새로운 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PwChangeActivity.this, "현재 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                break;
            case 1:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue)));
                break;
            case 2:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                break;
            case 3:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                break;
        }
    }
}
