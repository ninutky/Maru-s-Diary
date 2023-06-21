package com.example.maru_s_diary;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewPageActivity extends AppCompatActivity {

    private static final String TAG = "NewPageActivity";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mTitle, mContents, mDate;
    private ImageView appbar_iv, mPhoto;
    private LinearLayout writeLly;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;
    private Uri uri;

    //    HomeFragment homeFragment;
    Dialog dialog02,dialog03; // 커스텀 다이얼로그

    CircleImageView[] prfimgs;
    ImageView[] prfchks;
    CircleImageView mood_img,weather_img;
    ImageView back;

    String selectedMood = "emoji0";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 값 가져오기
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0); // 기본값은 0
        preferences = getPreferences(Context.MODE_PRIVATE);
        int themeColor = preferences.getInt("themeColor", R.color.pink_50); // 기본 테마 분홍색

        setContentView(R.layout.writing_diary);

        writeLly = findViewById(R.id.write_lly);
        appbar_iv = findViewById(R.id.appbar_iv);
        appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(themeColor)));
        changeTheme(theme);

        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);
        mDate = findViewById(R.id.date);
        mPhoto = findViewById(R.id.imgbtn);

//        LayoutInflater inflater = getLayoutInflater();
//        ViewGroup container = findViewById(R.id.container);
//
//        View v = inflater.inflate(R.layout.writing_diary, container, false);

        // homeFragment=new HomeFragment();
        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
                // upload();
                // show(uri);
            }
        });

        findViewById(R.id.post_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postId = mStore.collection(FirebaseID.post).document().getId();
                Map<String, Object> data = new HashMap<>();
                String uid = mAuth.getCurrentUser().getUid();
                data.put("owner", uid);
                data.put(FirebaseID.title, mTitle.getText().toString());
                data.put(FirebaseID.contents, mContents.getText().toString());
                data.put(FirebaseID.date, mDate.getText().toString());
                data.put("mood", selectedMood);
                data.put("postId",postId);
                String value1 = mTitle.getText().toString().trim();
                String value2 = mContents.getText().toString().trim();
                String value3 = mDate.getText().toString().trim();
                String[] values = {value1, value2, value3};
                boolean hasEmptyValue = false;
                for (String value : values) {
                    if (value.isEmpty()) {
                        hasEmptyValue = true;
                        break;
                    }
                }
                if (hasEmptyValue) {
                    // 필수 값 중 하나 이상이 비어있는 경우 처리할 로직 작성
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewPageActivity.this);
                    builder.setTitle("경고")
                            .setMessage("값을 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .show();
                } else {
                    // 모든 필수 값이 채워진 경우 파이어베이스로 전송하는 로직 작성
                    mStore
                            .collection(FirebaseID.post)
                            .add(data)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Log.d("mytag", "complete");
                                    Toast.makeText(NewPageActivity.this, "글이 등록되었습니다", Toast.LENGTH_SHORT).show();
                                    NewPageActivity.this.finish();
                                }
                            });
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT );
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });



        mood_img = (CircleImageView) findViewById(R.id.mood);
       // mood_llbtn = findViewById(R.id.mood);
        weather_img = (CircleImageView) findViewById(R.id.weather);
//        weather_llbtn = findViewById(R.id.weather);

//        dialog01 = new Dialog(NewPageActivity.this);
//        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog01.setContentView(R.layout.dialog01);

        dialog02 = new Dialog(NewPageActivity.this);
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog02.setContentView(R.layout.dialog02);

        dialog03 = new Dialog(NewPageActivity.this);
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

    }

    private void select() {
        /*
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT );
        intent.setType("image/*");
        launcher.launch(intent);

         */

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                if (intent != null) {
                    // Get the URI of the selected file
                    final Uri uri = intent.getData();
                    Log.d("mytag", uri.toString());
                    upload(uri);
                }
            }
            super.onActivityResult(requestCode, resultCode, intent);

        }
    }

    private void upload(Uri uri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(mAuth.getUid());
        storageReference.child("images").child("filename").putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(NewPageActivity.this, "업로드에 성공했습니다", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(NewPageActivity.this, "업로드에 실패했습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void show(Uri imageUri) {
        // Firebase Storage 참조 가져오기
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // 이미지 파일 이름 설정 (예: "images/profile.jpg")
        String fileName = "images/" + System.currentTimeMillis() + ".jpg";

        // Storage에 파일 업로드
        StorageReference imageRef = storageRef.child(fileName);
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // 업로드 성공 시
                    // ...
                })
                .addOnFailureListener(e -> {
                    // 업로드 실패 시
                    // ...
                });
    }

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();
                        Log.d("test", uri.toString());

                    }
                }
            });

    // dialog01을 디자인하는 함수
//    public void showDialog01(){
//        dialog01.show(); // 다이얼로그 띄우기
//        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경
//
//        // 아니오 버튼
//        Button noBtn = dialog01.findViewById(R.id.noBtn);
//        noBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                dialog01.dismiss(); // 다이얼로그 닫기
//            }
//        });
//        // 네 버튼
//        dialog01.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                finish();           // 앱 종료
//            }
//        });
//    }

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

                selectedMood = "emoji" + selectedMoodIndex;
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

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                writeLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                break;
            case 1:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue)));
                writeLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                break;
            case 2:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                writeLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                break;
            case 3:
                appbar_iv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                writeLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                break;
        }
    }


}