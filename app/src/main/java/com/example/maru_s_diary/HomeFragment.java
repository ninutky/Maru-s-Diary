package com.example.maru_s_diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
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
        mPostRecyclerView=v.findViewById(R.id.main_recyclerview);
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
        return v;
    }



}