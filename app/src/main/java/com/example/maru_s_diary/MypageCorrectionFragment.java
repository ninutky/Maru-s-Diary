package com.example.maru_s_diary;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MypageCorrectionFragment extends Fragment implements View.OnClickListener, PostAdapter.OnItemClickListener{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    public RecyclerView mPostRecyclerView;
    private EditText mTitle,mContents,mDate;
    //    private int mWeather,mFeeling;
    public PostAdapter mAdapter;
    private List<Post> mDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mypage_correction,container,false);
        View v2 = inflater.inflate(R.layout.writing_diary, container, false);

        mTitle=v2.findViewById(R.id.post_title_edit);
        mContents=v2.findViewById(R.id.post_contents_edit);
//        mWeather=findViewById(R.id.weather);
//        mFeeling=findViewById(R.id.mood);
        mDate=v2.findViewById(R.id.date);
        v2.findViewById(R.id.post_save_btn).setOnClickListener(this);


        mPostRecyclerView=v.findViewById(R.id.mypage_recyclerview);

        return v;
    }

    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mAdapter = new PostAdapter(getContext(), mDatas);
        mAdapter.setOnItemClickListener(this);
        mPostRecyclerView.setAdapter(mAdapter);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStore.collection(FirebaseID.post)
                .whereEqualTo("owner", userId)  // owner 필드가 현재 사용자의 ID와 일치하는 문서만 가져옴
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String title = documentSnapshot.getString("title");
                            String contents = documentSnapshot.getString("contents");
                            String documentId = documentSnapshot.getString("owner");
                            String date = documentSnapshot.getString("date");
                            //date 필드명이 2000/01/01이라서 불러오면 에러남 ㅠㅠ

                            Log.d("Firestore", "제목: " + title);
                            Log.d("Firestore", "내용: " + contents);
                            Log.d("Firestore", "아이디: " + documentId);
                            Log.d("Firestore", "날짜: " + date);

                            Post data = new Post(documentId, title, contents, date);
                            mDatas.add(data);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "일기 불러오기 실패", e);
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(mAuth.getCurrentUser()!=null){
            String postId=mStore.collection(FirebaseID.post).document().getId();
            Map<String,Object> data=new HashMap<>();
            data.put(FirebaseID.documentId,mAuth.getCurrentUser().getUid());
            data.put(FirebaseID.title,mTitle.getText().toString());
            data.put(FirebaseID.contents,mContents.getText().toString());
//            data.put(String.valueOf(FirebaseID.weather), mWeather);
//            data.put(String.valueOf(FirebaseID.feeling), mFeeling);
            data.put(FirebaseID.date,mDate.getText().toString());

            mStore.collection(FirebaseID.post).document(postId).set(data, SetOptions.merge());

        }

    }

    // 좋아요 버튼 클릭 이벤트 처리
    @Override
    public void onHeartBtnClick(int position) {

        Toast.makeText(getActivity(), "좋아요 되었습니다. - position: " + position, Toast.LENGTH_SHORT).show();
    }

    // 일기 클릭 이벤트 처리
    @Override
    public void onLlyClick(int position) {
        // 프래그먼트 전환 코드 작성
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 전환할 프래그먼트 생성 및 추가 작업
        Diary newFragment = new Diary();
        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        fragmentTransaction.addToBackStack(null);

        // 프래그먼트 전환 실행
        fragmentTransaction.commit();
    }
}
