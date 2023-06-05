package com.example.maru_s_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DeleteAccountActivity extends AppCompatActivity {
    ImageButton backBtn;
    EditText passwordEditText;
    Button deleteAccountBtn;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        backBtn = findViewById(R.id.backBtn);
        passwordEditText = findViewById(R.id.et_id);
        deleteAccountBtn = findViewById(R.id.deleteBtn);

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
}
