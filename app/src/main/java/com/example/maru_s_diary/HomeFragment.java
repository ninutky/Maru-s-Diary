package com.example.maru_s_diary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener, PostAdapter.OnItemClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    public RecyclerView mPostRecyclerView;
    private EditText mTitle,mContents,mDate;
    //    private int mWeather,mFeeling;
    public PostAdapter mAdapter;
    private List<Post> mDatas;
    FloatingActionButton writeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        View v2 = inflater.inflate(R.layout.writing_diary, container, false);

        mTitle=v2.findViewById(R.id.post_title_edit);
        mContents=v2.findViewById(R.id.post_contents_edit);
//        mWeather=findViewById(R.id.weather);
//        mFeeling=findViewById(R.id.mood);
        mDate=v2.findViewById(R.id.date);
        writeButton=v.findViewById(R.id.btn_write);
        v2.findViewById(R.id.post_save_btn).setOnClickListener(this);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewPageActivity.class);
                startActivity(intent);
            }
        });

        mPostRecyclerView=v.findViewById(R.id.main_recyclerview);
//        mDatas.add(new Post(null,"title","contents","2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));
//        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30"));


//        // 리사이클러뷰 아이템 클릭 리스너 구현
//        mPostRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                // 사용자가 터치한 좌표를 확인하여 클릭한 아이템 가져오기
//                View childView = rv.findChildViewUnder(e.getX(), e.getY());
//                int position = rv.getChildAdapterPosition(childView);
//
//                // 클릭한 아이템의 프래그먼트 전환을 위한 작업 수행
//                if (position != RecyclerView.NO_POSITION) {
//                    // 프래그먼트 전환 코드 작성
//                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                    // 전환할 프래그먼트 생성 및 추가 작업
//                    Diary newFragment = new Diary();
//                    fragmentTransaction.replace(R.id.fragment_container, newFragment);
//                    fragmentTransaction.addToBackStack(null);
//
//                    // 프래그먼트 전환 실행
//                    fragmentTransaction.commit();
//                }
//
//                return true;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                // 터치 이벤트 처리
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                // 인터셉트 터치 이벤트 요청 처리
//            }
//        });

        return v;
    }



    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mAdapter = new PostAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        mPostRecyclerView.setAdapter(mAdapter);

        mStore.collection(FirebaseID.post)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult()!=null){
                                for(DocumentSnapshot snap:task.getResult()){
                                    Map<String,Object> shot= snap.getData();
                                    String documentId=String.valueOf(shot.get(FirebaseID.documentId));
                                    String title=String.valueOf(snap.get(FirebaseID.title));
                                    String contents=String.valueOf(shot.get(FirebaseID.contents));
                                    String date=String.valueOf(shot.get(FirebaseID.date));
                                    Post data=new Post(documentId,title,contents,date);
                                    mDatas.add(data);
                                }
                                mAdapter.notifyDataSetChanged(); // 데이터 변경을 어댑터에 알려줌
                            }
                        }
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