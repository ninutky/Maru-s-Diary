package com.example.maru_s_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Modification extends AppCompatActivity {

    private static final String TAG = "Modification";

    Dialog dialog01,dialog02,dialog03; // 커스텀 다이얼로그

    CircleImageView[] prfimgs;
    ImageView[] prfchks;
    CircleImageView mood_img,weather_img;
    LinearLayout mood_llbtn,weather_llbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correction_diary);
    }


    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.writing_diary, container, false);

        mood_img = (CircleImageView) v.findViewById(R.id.mood);
        mood_llbtn = v.findViewById(R.id.mood);
        weather_img = (CircleImageView) v.findViewById(R.id.weather);
        weather_llbtn = v.findViewById(R.id.weather);

        dialog01 = new Dialog(Modification.this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.dialog01);

        dialog02 = new Dialog(Modification.this);
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog02.setContentView(R.layout.dialog02);

        dialog03 = new Dialog(Modification.this);
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog03.setContentView(R.layout.dialog03);

        mood_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog02();
                }
        });

        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog03();
                }
        });

        return v;
    }

    public void showDialog02(){
        dialog02.show(); // 다이얼로그 띄우기
        dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
        dialog02.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "기분이 저장되었습니다.",Toast.LENGTH_SHORT).show();
                dialog02.dismiss();

                //기분저장
                int selectedMoodIndex = -1;
                for (int i = 0; i < 9; i++) {
                    if (prfchks[i].getVisibility() == View.VISIBLE) {
                        selectedMoodIndex = i;
                        break;
                    }
                }

                if (selectedMoodIndex == 0) {
                    mood_img.setImageResource(R.drawable.mood1);
                } else if (selectedMoodIndex == 1) {
                    mood_img.setImageResource(R.drawable.mood2);
                } else if (selectedMoodIndex == 2) {
                    mood_img.setImageResource(R.drawable.mood3);
                } else if (selectedMoodIndex == 3) {
                    mood_img.setImageResource(R.drawable.mood4);
                } else if (selectedMoodIndex == 4) {
                    mood_img.setImageResource(R.drawable.mood5);
                } else if (selectedMoodIndex == 5) {
                    mood_img.setImageResource(R.drawable.mood6);
                } else if (selectedMoodIndex == 6) {
                    mood_img.setImageResource(R.drawable.mood7);
                } else if (selectedMoodIndex == 7) {
                    mood_img.setImageResource(R.drawable.mood8);
                } else {
                    mood_img.setImageResource(R.drawable.mood9);
                }
            }
        });

        dialog02.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog02.dismiss();
            }
        });

        prfimgs = new CircleImageView[9];
        prfimgs[0] = (dialog02.findViewById(R.id.mood_img_1));
        prfimgs[1] = (dialog02.findViewById(R.id.mood_img_2));
        prfimgs[2] = (dialog02.findViewById(R.id.mood_img_3));
        prfimgs[3] = (dialog02.findViewById(R.id.mood_img_4));
        prfimgs[4] = (dialog02.findViewById(R.id.mood_img_5));
        prfimgs[5] = (dialog02.findViewById(R.id.mood_img_6));
        prfimgs[6] = (dialog02.findViewById(R.id.mood_img_7));
        prfimgs[7] = (dialog02.findViewById(R.id.mood_img_8));
        prfimgs[8] = (dialog02.findViewById(R.id.mood_img_9));

        prfchks = new ImageView[9];
        prfchks[0] = (dialog02.findViewById(R.id.mood_chk_1));
        prfchks[1] = (dialog02.findViewById(R.id.mood_chk_2));
        prfchks[2] = (dialog02.findViewById(R.id.mood_chk_3));
        prfchks[3] = (dialog02.findViewById(R.id.mood_chk_4));
        prfchks[4] = (dialog02.findViewById(R.id.mood_chk_5));
        prfchks[5] = (dialog02.findViewById(R.id.mood_chk_6));
        prfchks[6] = (dialog02.findViewById(R.id.mood_chk_7));
        prfchks[7] = (dialog02.findViewById(R.id.mood_chk_8));
        prfchks[8] = (dialog02.findViewById(R.id.mood_chk_9));

        for(int i = 0; i < 9; i++){
            int finalI = i;
            prfimgs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < 9; j++) {
                        prfchks[j].setVisibility(View.INVISIBLE);
                    }
                    prfchks[finalI].setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void showDialog03(){
        dialog03.show(); // 다이얼로그 띄우기
        dialog03.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
        dialog03.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "기분이 저장되었습니다.",Toast.LENGTH_SHORT).show();
                dialog03.dismiss();

                //기분저장
                int selectedMoodIndex = -1;
                for (int i = 0; i < 6; i++) {
                    if (prfchks[i].getVisibility() == View.VISIBLE) {
                        selectedMoodIndex = i;
                        break;
                    }
                }

                if (selectedMoodIndex == 0) {
                    weather_img.setImageResource(R.drawable.sun);
                } else if (selectedMoodIndex == 1) {
                    weather_img.setImageResource(R.drawable.cloud);
                } else if (selectedMoodIndex == 2) {
                    weather_img.setImageResource(R.drawable.wind);
                } else if (selectedMoodIndex == 3) {
                    weather_img.setImageResource(R.drawable.rain);
                } else if (selectedMoodIndex == 4) {
                    weather_img.setImageResource(R.drawable.lighntnong);
                } else {
                    weather_img.setImageResource(R.drawable.snow);
                }
            }
        });

        dialog02.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog03.dismiss();
            }
        });

        prfimgs = new CircleImageView[6];
        prfimgs[0] = (dialog03.findViewById(R.id.weather_img_1));
        prfimgs[1] = (dialog03.findViewById(R.id.weather_img_2));
        prfimgs[2] = (dialog03.findViewById(R.id.weather_img_3));
        prfimgs[3] = (dialog03.findViewById(R.id.weather_img_4));
        prfimgs[4] = (dialog03.findViewById(R.id.weather_img_5));
        prfimgs[5] = (dialog03.findViewById(R.id.weather_img_6));

        prfchks = new ImageView[6];
        prfchks[0] = (dialog03.findViewById(R.id.weather_chk_1));
        prfchks[1] = (dialog03.findViewById(R.id.weather_chk_2));
        prfchks[2] = (dialog03.findViewById(R.id.weather_chk_3));
        prfchks[3] = (dialog03.findViewById(R.id.weather_chk_4));
        prfchks[4] = (dialog03.findViewById(R.id.weather_chk_5));
        prfchks[5] = (dialog03.findViewById(R.id.weather_chk_6));

        for(int i = 0; i < 6; i++){
            int finalI = i;
            prfimgs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < 6; j++) {
                        prfchks[j].setVisibility(View.INVISIBLE);
                    }
                    prfchks[finalI].setVisibility(View.VISIBLE);
                }
            });
        }
    }

}