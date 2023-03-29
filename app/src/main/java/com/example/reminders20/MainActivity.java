package com.example.reminders20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.container, new ListFragment(), "fragment1").addToBackStack("Activity").commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }
}