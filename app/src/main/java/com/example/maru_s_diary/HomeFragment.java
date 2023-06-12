package com.example.maru_s_diary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mDatas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mPostRecyclerView = v.findViewById(R.id.main_recyclerview);
        mDatas=new ArrayList<>();
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));
        mDatas.add(new Post(null,"title","contents",1,1,"2023/05/30",10));

        mAdapter=new PostAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdapter);

        // 리사이클러뷰 아이템 클릭 리스너 구현
        mPostRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // 사용자가 터치한 좌표를 확인하여 클릭한 아이템 가져오기
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(childView);

                // 클릭한 아이템의 프래그먼트 전환을 위한 작업 수행
                if (position != RecyclerView.NO_POSITION) {
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

                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // 터치 이벤트 처리
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                // 인터셉트 터치 이벤트 요청 처리
            }
        });

        return v;
    }

}