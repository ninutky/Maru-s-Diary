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
//        changeTheme(0);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data=datas.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.date.setText(data.getDate());
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
        setItemColorBasedOnNumber(holder);
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

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.list_title);
            contents=itemView.findViewById(R.id.list_detail);
            date=itemView.findViewById(R.id.list_datetime);
            heartBtn = itemView.findViewById(R.id.heart_btn);
            diaryLly = itemView.findViewById(R.id.diary_lly);
            moodImage = itemView.findViewById(R.id.list_feeling_img);
            weatherImage=itemView.findViewById(R.id.list_weather_img);

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

//    public void changeTheme(int n) {
//        switch (n) {
//            case 0:
//            default:
//                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.pink_50)));
//                Log.d("mytag","pink_50");
//                break;
//            case 1:
//                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.skyblue_50)));
//                Log.d("mytag","skyblue_50");
//                break;
//            case 2:
//                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.green_50)));
//                Log.d("mytag","green_50");
//                break;
//            case 3:
//                diaryLly.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.yellow_50)));
//                Log.d("mytag","yellow_50");
//                break;
//        }
//    }

    // SharedPreferences에서 값을 가져와서 색상을 변경하는 메서드
    private void setItemColorBasedOnNumber(RecyclerView.ViewHolder holder) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt("theme", 0);

        switch (number) {
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
