package com.example.maru_s_diary;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> datas;
    private OnItemClickListener mListener;
    private Context context;
    private LinearLayout diaryLly;
    private SharedPreferences sharedPreferences;

    public PostAdapter(Context context, List<Post> datas) {
        this.context = context;
        this.datas = datas;
        sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        diaryLly = itemView.findViewById(R.id.diary_lly);
        changeTheme(3);
        return new PostViewHolder(itemView);
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

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.list_title);
            contents=itemView.findViewById(R.id.list_detail);
            date=itemView.findViewById(R.id.list_datetime);
            heartBtn = itemView.findViewById(R.id.heart_btn);
            diaryLly = itemView.findViewById(R.id.diary_lly);

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

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.pink_50)));
                Log.d("mytag","pink_50");
                break;
            case 1:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.skyblue_50)));
                Log.d("mytag","skyblue_50");
                break;
            case 2:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.green_50)));
                Log.d("mytag","green_50");
                break;
            case 3:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.yellow_50)));
                Log.d("mytag","yellow_50");
                break;
        }
    }

}
