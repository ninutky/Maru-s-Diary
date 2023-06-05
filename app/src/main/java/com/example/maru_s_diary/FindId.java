package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindId extends AppCompatActivity {
    EditText email;
    Button nextBtn;
    ImageButton backbtn;
    private DatabaseReference userRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find);

        email = findViewById(R.id.fi_id);
        nextBtn = findViewById(R.id.nextbutton);
        backbtn = findViewById(R.id.id_backbtn);
        userRef = FirebaseDatabase.getInstance().getReference("user");

        // 이전 버튼
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 다음버튼을 누를시
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = email.getText().toString().trim();

                // 사용자 정보 조회
                userRef.orderByChild("email").equalTo(inputEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists()) {
                            // 이메일과 일치하는 사용자 정보를 찾음
                            for(DataSnapshot userSnapshot : datasnapshot.getChildren()) {
                                String foundId = userSnapshot.child("id").getValue(String.class);
                                Toast.makeText(FindId.this, "아이디 : "+foundId, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Toast.makeText(FindId.this, "일치하는 아이디를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 조회 취소 또는 실패
                        Toast.makeText(FindId.this, "사용자 정보 조회에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}