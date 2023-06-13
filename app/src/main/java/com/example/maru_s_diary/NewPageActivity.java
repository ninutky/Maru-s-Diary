package com.example.maru_s_diary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPageActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mTitle, mContents, mDate;
//    private int mWeather,mFeeling;
    public PostAdapter mAdapter;
    private List<Post> mDatas;
//    HomeFragment homeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_diary);
        mTitle=findViewById(R.id.post_title_edit);
        mContents=findViewById(R.id.post_contents_edit);
//      mWeather=findViewById(R.id.weather);
//        mFeeling=findViewById(R.id.mood);
        mDate=findViewById(R.id.date);
       // homeFragment=new HomeFragment();
        findViewById(R.id.post_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postId=mStore.collection(FirebaseID.post).document().getId();
                Map<String,Object> data=new HashMap<>();
                String uid = mAuth.getCurrentUser().getUid();
                data.put("owner", uid);
                data.put(FirebaseID.title,mTitle.getText().toString());
                data.put(FirebaseID.contents,mContents.getText().toString());
                data.put(FirebaseID.date,mDate.getText().toString());
                mStore
                        .collection(FirebaseID.post)
                        .add(data)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Log.d("mytag", "complete");
                                Toast.makeText(NewPageActivity.this, "글이 등록되었습닏", Toast.LENGTH_SHORT).show();
                                NewPageActivity.this.finish();
                            }
                        });
            }
        });



    }





}
