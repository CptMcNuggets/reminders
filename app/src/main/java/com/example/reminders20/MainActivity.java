package com.example.reminders20;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    public ReminderDatabase reminderDB;
    private static final String REMINDER_DB_NAME = "reminder_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListFragment()).addToBackStack(ListFragment.class.getCanonicalName()).commit();
        reminderDB = Room.databaseBuilder(getApplicationContext(), ReminderDatabase.class, REMINDER_DB_NAME).build();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }
}