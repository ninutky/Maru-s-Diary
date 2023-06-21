package com.example.maru_s_diary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {
    TextView delaccount_tvbtn, changepw_tvbtn, alram_tvbtn, changetheme_tvbtn;

    LinearLayout logout_llbtn, profile_llbtn;
    Dialog loutdlg, themedlg, profiledlg;
    TextView loginOrLogoutTv;
    CircleImageView profile_img;
    LinearLayout[] themes;
    CircleImageView[] prfimgs;
    ImageView[] thmchks, prfchks;
    ImageView logoutImg;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private TextView userIdTextView;    // 로그인되어 있는 아이디
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 관련
    private DatabaseReference mDatabaseReference; // 데이터베이스 관련련
    //private FirebaseStorage mStorage;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
       //mStorage = FirebaseStorage.getInstance();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        profile_llbtn = v.findViewById(R.id.profile_llbtn);
        profile_img = (CircleImageView) v.findViewById(R.id.set_profile_img);
        userIdTextView = v.findViewById(R.id.set_id_tv);

        changetheme_tvbtn = v.findViewById(R.id.set_changetheme_tvbtn);
        alram_tvbtn = v.findViewById(R.id.set_alram_tvbtn);
//        changepw_tvbtn = v.findViewById(R.id.set_changepw_tvbtn);
        logout_llbtn = v.findViewById(R.id.set_login_or_logout_llbtn);
        delaccount_tvbtn = v.findViewById(R.id.set_delaccount_tvbtn);
        loginOrLogoutTv = v.findViewById(R.id.login_or_logout_tv);
        logoutImg = v.findViewById(R.id.logout_img);

        loutdlg = new Dialog(getActivity());
        loutdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loutdlg.setContentView(R.layout.fragment_logout_dialog);

        themedlg = new Dialog(getActivity());
        themedlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        themedlg.setContentView(R.layout.fragment_theme_dialog);

        profiledlg = new Dialog(getActivity());
        profiledlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        profiledlg.setContentView(R.layout.fragment_profile_dialog);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            String email = currentUser.getEmail();
            if(email != null){
                String displayEmail = email.substring(0, email.indexOf('@'));
                userIdTextView.setText(displayEmail);
                loginOrLogoutTv.setText("계정 로그아웃");
                logoutImg.setVisibility(View.VISIBLE);
            }


        } else {
            userIdTextView.setText("(null)");
            loginOrLogoutTv.setText("로그인 / 회원가입");
            logoutImg.setVisibility(View.INVISIBLE);

        }

        delaccount_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser != null) {
                    Intent intent = new Intent(getActivity(), DeleteAccountActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "로그인 후 사용해주세요.", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

//        changepw_tvbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), PwChangeActivity.class);
//                startActivity(intent);
//            }
//        });

        logout_llbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser != null) showloutDialog();
                else {
                    Intent intent = new Intent(getActivity(), SigninActivity.class);
                    startActivity(intent);
                }
            }

        });

        alram_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
                startActivity(intent);
            }
        });

        changetheme_tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showthemeDialog();
            }
        });

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileDialog();
            }
        });

        preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = preferences.edit();
        int themeColor = preferences.getInt("themeColor", R.color.pink_50); // 기본 테마 분홍색
        // 기존에 저장한 테마 가져옴
        profile_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        logout_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));

        int profile = preferences.getInt("profile", R.drawable.pink_rabbit); // 기본 테마 분홍색
        // 기존에 저장한 프로필가져옴
        profile_img.setImageResource(profile);

        return v;

    }

    public void showloutDialog(){
        loutdlg.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // dialog 라운드 끝 하얀 배경 삭제
        loutdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 아니오 버튼
        Button noBtn = loutdlg.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                loutdlg.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        loutdlg.findViewById(R.id.yesBtn).setOnClickListener(v -> {
           mFirebaseAuth.signOut();
           Intent intent = new Intent(getActivity(), SigninActivity.class);
           startActivity(intent);
           getActivity().finish();
        });

    }

    public void showthemeDialog(){
        themedlg.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // dialog 라운드 끝 하얀 배경 삭제
        themedlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 저장 버튼
        themedlg.findViewById(R.id.theme_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "테마가 저장되었습니다.",Toast.LENGTH_SHORT).show();
                themedlg.dismiss();
                // 원하는 기능 구현
                // 테마 저장
                int selectedThemeIndex = -1;
                for (int i = 0; i < 4; i++) {
                    if (thmchks[i].getVisibility() == View.VISIBLE) {
                        selectedThemeIndex = i;
                        break;
                    }
                }

                if (selectedThemeIndex == 0) {
                    editor.putInt("theme", 0);  // theme에 type 0 전달 (테마 변경)
                    editor.putInt("themeColor", R.color.pink_50);   // 색 전달 (LinearLayout 변경)
                    profile_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                    changetheme_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                    alram_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
//                    changepw_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                    logout_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                    delaccount_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));

                } else if (selectedThemeIndex == 1) {
                    editor.putInt("theme", 1);
                    editor.putInt("themeColor", R.color.skyblue_50);
                    profile_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                    changetheme_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                    alram_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
//                    changepw_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                    logout_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                    delaccount_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));

                } else if (selectedThemeIndex == 2) {
                    editor.putInt("theme", 2);
                    editor.putInt("themeColor", R.color.green_50);
                    profile_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                    changetheme_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                    alram_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
//                    changepw_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                    logout_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                    delaccount_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));

                } else {
                    editor.putInt("theme", 3);
                    editor.putInt("themeColor", R.color.yellow_50);
                    profile_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                    changetheme_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                    alram_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
//                    changepw_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                    logout_llbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                    delaccount_tvbtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                }
                editor.apply();

                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }

                Diary diaryFragment = (Diary) requireActivity().getSupportFragmentManager().findFragmentByTag("diary_fragment");
                if (diaryFragment != null) {
                    diaryFragment.setSharedPreferences(preferences);
                }

            }
        });

        themedlg.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themedlg.dismiss();
            }
        });

        themes = new LinearLayout[4];
        themes[0] = (themedlg.findViewById(R.id.theme_pink));
        themes[1] = (themedlg.findViewById(R.id.theme_skyblue));
        themes[2] = (themedlg.findViewById(R.id.theme_green));
        themes[3] = (themedlg.findViewById(R.id.theme_yellow));

        themes = new LinearLayout[4];
        themes[0] = (themedlg.findViewById(R.id.theme_pink));
        themes[1] = (themedlg.findViewById(R.id.theme_skyblue));
        themes[2] = (themedlg.findViewById(R.id.theme_green));
        themes[3] = (themedlg.findViewById(R.id.theme_yellow));

        thmchks = new ImageView[4];
        thmchks[0] = (themedlg.findViewById(R.id.pink_check));
        thmchks[1] = (themedlg.findViewById(R.id.skyblue_check));
        thmchks[2] = (themedlg.findViewById(R.id.green_check));
        thmchks[3] = (themedlg.findViewById(R.id.yellow_check));

        for(int i = 0; i < 4; i++) {
            int finalI = i;
            themes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < 4; j++) {
                        thmchks[j].setVisibility(View.INVISIBLE);
                    }
                    thmchks[finalI].setVisibility(View.VISIBLE);

                }
            });
        }

    }

    public void showProfileDialog() {
        profiledlg.show(); // 다이얼로그 띄우기

        // dialog 라운드 끝 하얀 배경 삭제
        profiledlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 저장 버튼
        profiledlg.findViewById(R.id.profile_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "프로필 사진이 저장되었습니다.",Toast.LENGTH_SHORT).show();
                profiledlg.dismiss();

                // 프로필 저장
                int selectedProfileIndex = -1;
                for (int i = 0; i < 6; i++) {
                    if (prfchks[i].getVisibility() == View.VISIBLE) {
                        selectedProfileIndex = i;
                        break;
                    }
                }

                if (selectedProfileIndex == 0) {
                    editor.putInt("profile", R.drawable.pink_rabbit);
                    profile_img.setImageResource(R.drawable.pink_rabbit);
                } else if (selectedProfileIndex == 1) {
                    editor.putInt("profile", R.drawable.blue_rabbit);
                    profile_img.setImageResource(R.drawable.blue_rabbit);
                } else if (selectedProfileIndex == 2) {
                    editor.putInt("profile", R.drawable.yellow_rabbit);
                    profile_img.setImageResource(R.drawable.yellow_rabbit);
                } else if (selectedProfileIndex == 3) {
                    editor.putInt("profile", R.drawable.orange_rabbit);
                    profile_img.setImageResource(R.drawable.orange_rabbit);
                } else if (selectedProfileIndex == 4) {
                    editor.putInt("profile", R.drawable.mint_rabbit);
                    profile_img.setImageResource(R.drawable.mint_rabbit);
                } else {
                    editor.putInt("profile", R.drawable.purple_rabbit);
                    profile_img.setImageResource(R.drawable.purple_rabbit);
                }
                editor.apply();
            }
        });

        profiledlg.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profiledlg.dismiss();
            }
        });

        prfimgs = new CircleImageView[6];
        prfimgs[0] = (profiledlg.findViewById(R.id.profile_img_1));
        prfimgs[1] = (profiledlg.findViewById(R.id.profile_img_2));
        prfimgs[2] = (profiledlg.findViewById(R.id.profile_img_3));
        prfimgs[3] = (profiledlg.findViewById(R.id.profile_img_4));
        prfimgs[4] = (profiledlg.findViewById(R.id.profile_img_5));
        prfimgs[5] = (profiledlg.findViewById(R.id.profile_img_6));

        prfchks = new ImageView[6];
        prfchks[0] = (profiledlg.findViewById(R.id.profile_chk_1));
        prfchks[1] = (profiledlg.findViewById(R.id.profile_chk_2));
        prfchks[2] = (profiledlg.findViewById(R.id.profile_chk_3));
        prfchks[3] = (profiledlg.findViewById(R.id.profile_chk_4));
        prfchks[4] = (profiledlg.findViewById(R.id.profile_chk_5));
        prfchks[5] = (profiledlg.findViewById(R.id.profile_chk_6));

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
