package com.example.reminders20;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    public ReminderDatabase reminderDB;
    public ReminderDao reminderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListFragment()).addToBackStack("Fragment_List").commit();
        reminderDB = Room.databaseBuilder(getApplicationContext(), ReminderDatabase.class, "reminder_db").build();
        reminderDao = reminderDB.reminderDao();
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