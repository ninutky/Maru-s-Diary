package com.example.maru_s_diary;

import static android.content.Context.MODE_PRIVATE;

import static com.example.maru_s_diary.FirebaseID.documentId;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

public class Diary extends Fragment {
    private ImageButton backBtn;
    private ImageButton reportBtn;
    private Dialog reportdlg;
    private LinearLayout diaryLly;
    private RadioGroup radioGroup;
    private TextView mDate;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary, container, false);

        // Find the TextViews by their respective IDs
        TextView contentsTextView = v.findViewById(R.id.contents);
        TextView titleTextView = v.findViewById(R.id.title);

        mDate=v.findViewById(R.id.dates);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            String formatedNow = now.format(formatter);
            mDate.setText(formatedNow);
        }
        LocalDate now;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            loadDataFromFirestore(documentId, mDate, contentsTextView, titleTextView);
            String formatedNow = now.format(formatter);

            Bundle args = getArguments();
            if (args != null) {
                String documentId = args.getString("documentId");
                // Load data from Firestore using the documentId
                mDate.setText(formatedNow);
            }
        }


        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        reportBtn = v.findViewById(R.id.report);
        reportdlg = new Dialog(getActivity());
        reportdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reportdlg.setContentView(R.layout.fragment_report_dialog);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReportDialog();
            }
        });
        reportdlg.findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportdlg.dismiss();
            }
        });
        radioGroup = reportdlg.findViewById(R.id.report_group);
        reportdlg.findViewById(R.id.report_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    Toast.makeText(getContext(), "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    reportdlg.dismiss();
                } else {
                    Toast.makeText(getContext(), "신고 사유를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        diaryLly = v.findViewById(R.id.diary_lly);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", 0);
        changeTheme(theme);

        return v;
    }

    public void showReportDialog(){
        reportdlg.show(); // 다이얼로그 띄우기
        reportdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void changeTheme(int n) {
        switch (n) {
            case 0:
            default:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink_50)));
                break;
            case 1:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.skyblue_50)));
                break;
            case 2:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_50)));
                break;
            case 3:
                diaryLly.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_50)));
                break;
        }
    }


    private void loadDataFromFirestore(String documentId, TextView datesTextView, TextView contentsTextView, TextView titleTextView) {
        // Load the data from Firestore or any other data source based on the documentId
        // For example, assuming you have a Firestore collection called "posts" and you want to load data from a document with the given documentId
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("post").document(documentId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Extract the data from the document
                            String contents = documentSnapshot.getString("contents");
                            String title = documentSnapshot.getString("title");

                            // Set the data to the respective TextViews
                            contentsTextView.setText(contents);
                            titleTextView.setText(title);
                        } else {
                            Log.d("Diary", "No such document");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Diary", "Error retrieving document", e);
                    }
                });
    }

}