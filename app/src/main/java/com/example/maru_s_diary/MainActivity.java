package com.example.maru_s_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private PwChangeFragment fragmentA;
    private PwChangeFragment2 fragmentB;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        fragmentA = new PwChangeFragment();
        fragmentB = new PwChangeFragment2();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentB).commitAllowingStateLoss();

        setContentView(R.layout.activity_main);
    }
}