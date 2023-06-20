package com.example.maru_s_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DeleteAccountActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private EditText passwordEditText;
    private Button deleteAccountBtn;
    private ImageView appbar_iv;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 값 가져오기
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0); // 기본값은 0
        preferences = getPreferences(Context.MODE_PRIVATE);
        int themeColor = preferences.getInt("themeColor", R.color.pink_50); // 기본 테마 분홍색

        setContentView(R.layout.activity_delete_account);

        appbar_iv = findViewById(R.id.appbar_iv);
        appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        changeTheme(theme);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        passwordEditText = findViewById(R.id.et_id);
        deleteAccountBtn = findViewById(R.id.deleteBtn);

        // 뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordEditText.getText().toString().trim();

                if (password.isEmpty()) {
                    Toast.makeText(DeleteAccountActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AuthCredential credential = EmailAuthProvider.getCredential(mUser.getEmail(), password);
                mUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    deleteAccount();
                                    Intent intent = new Intent(DeleteAccountActivity.this, SigninActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DeleteAccountActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void deleteAccount() {
        mUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DeleteAccountActivity.this, "계정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(DeleteAccountActivity.this, "계정 삭제에 실패하였습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
