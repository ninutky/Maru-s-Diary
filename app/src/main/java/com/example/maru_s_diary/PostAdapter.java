package com.example.maru_s_diary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> datas;
    private OnItemClickListener mListener;

    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data=datas.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.date.setText(data.getDate());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnItemClickListener {
        void onHeartBtnClick(int position);
        void onLlyClick(int position);
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView contents;
        private TextView date;
        private ImageButton heartBtn;
        private LinearLayout diaryLly;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.list_title);
            contents=itemView.findViewById(R.id.list_detail);
            date=itemView.findViewById(R.id.list_datetime);
            heartBtn = itemView.findViewById(R.id.heart_btn);
            diaryLly = itemView.findViewById(R.id.diary_lly);

            // 좋아요 버튼 클릭 이벤트 처리
            heartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onHeartBtnClick(position);
                        }
                    }
                }
            });

            diaryLly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onLlyClick(position);
                        }
                    }
                }
            });
            
        }
    }

    // 외부에서 리스너를 설정하기 위한 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
