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
import android.widget.ImageView;
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
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int heartCnt;

    public PostAdapter(Context context, List<Post> datas) {
        this.context = context;
        this.datas = datas;
        preferences = context.getSharedPreferences("heart", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        diaryLly = itemView.findViewById(R.id.diary_lly);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data=datas.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.date.setText(data.getDate());
        heartCnt = preferences.getInt("heart", 0); // 기존 값 가져와서 초기화

        String mood = data.getMood();
        if(mood.equals("emoji0")) holder.moodImage.setImageResource(R.drawable.mood1);
        if(mood.equals("emoji1")) holder.moodImage.setImageResource(R.drawable.mood2);
        if(mood.equals("emoji2")) holder.moodImage.setImageResource(R.drawable.mood3);
        if(mood.equals("emoji3")) holder.moodImage.setImageResource(R.drawable.mood4);
        if(mood.equals("emoji4")) holder.moodImage.setImageResource(R.drawable.mood5);
        if(mood.equals("emoji5")) holder.moodImage.setImageResource(R.drawable.mood6);
        if(mood.equals("emoji6")) holder.moodImage.setImageResource(R.drawable.mood7);
        if(mood.equals("emoji7")) holder.moodImage.setImageResource(R.drawable.mood8);
        if(mood.equals("emoji8")) holder.moodImage.setImageResource(R.drawable.mood9);
        String weather=data.getWeather();
        if(weather.equals("weather0")) holder.weatherImage.setImageResource(R.drawable.sun);
        if(weather.equals("weather1")) holder.weatherImage.setImageResource(R.drawable.cloud);
        if(weather.equals("weather2")) holder.weatherImage.setImageResource(R.drawable.wind);
        if(weather.equals("weather3")) holder.weatherImage.setImageResource(R.drawable.rain);
        if(weather.equals("weather4")) holder.weatherImage.setImageResource(R.drawable.lighntnong);
        if(weather.equals("weather5")) holder.weatherImage.setImageResource(R.drawable.snow);
        String heart=data.getHeart();
        String postId=data.getPostId();

        // 항목의 "좋아요" 수를 설정합니다.
        holder.heartText.setText(String.valueOf(heartCnt));
        setItemColorBasedOnNumber(holder);

        // 좋아요 버튼에 클릭 리스너를 설정합니다.
        holder.heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        heartCnt = Integer.parseInt(String.valueOf(preferences.getInt("heart", 0)));    // 기존 하트 값 불러오기
                        // 선택한 항목의 "좋아요" 수를 증가
                        heartCnt++;
                        holder.heartText.setText(String.valueOf(heartCnt));

                        // 업데이트된 데이터 객체를 다시 목록에 저장
                        editor.putInt("heart", heartCnt).apply();
                        data.setHeart(String.valueOf(heartCnt));
                        Log.d("mytag", "post: "+heartCnt);

                        // 리스너에게 클릭 이벤트를 알림
                        mListener.onHeartBtnClick(position);
                    }
                }
            }
        });
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
        private ImageView moodImage;
        private ImageView weatherImage;
        private TextView heartText;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.list_title);
            contents=itemView.findViewById(R.id.list_detail);
            date=itemView.findViewById(R.id.list_datetime);
            heartBtn = itemView.findViewById(R.id.heart_btn);
            diaryLly = itemView.findViewById(R.id.diary_lly);
            moodImage = itemView.findViewById(R.id.list_feeling_img);
            weatherImage=itemView.findViewById(R.id.list_weather_img);
            heartText=itemView.findViewById(R.id.heart_count);
            heartText.setText(
                    "" + heartCnt
            );


//            heartBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            heartText.setText("" + heartCnt);
//
//                            heartCnt++;
//                            heartText.setText(
//                                "" + heartCnt
//                            );
//                            editor.putInt("heart", heartCnt);
//                            editor.apply();
//
//                        }
//                    }
//                }
//            });
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

    // SharedPreferences에서 값을 가져와서 색상을 변경하는 메서드
    private void setItemColorBasedOnNumber(RecyclerView.ViewHolder holder) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt("theme", 0);

        switch (number) {
            case 0:
            default:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.pink_50)));
                break;
            case 1:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.skyblue_50)));
                break;
            case 2:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.green_50)));
                break;
            case 3:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.yellow_50)));
                break;
        }
    }

}
