package com.example.reminders20;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public ReminderDatabase reminderDB;
    public ReminderDao reminderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.container, new ListFragment(), "fragment_list").addToBackStack("Fragment_List").commit();
        reminderDB = Room.databaseBuilder(getApplicationContext(), ReminderDatabase.class, "reminder_db").build();
        reminderDao = reminderDB.reminderDao();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }
}