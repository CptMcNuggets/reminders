package com.example.reminders20.di.modules;

import static com.example.reminders20.MainActivity.REMINDER_DB_NAME;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.reminders20.RemindersApplication;
import com.example.reminders20.db.ReminderDao;
import com.example.reminders20.db.ReminderDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Provides
    @Singleton
    ReminderDatabase provideReminderDatabase(RemindersApplication application) {
        return Room.databaseBuilder(application, ReminderDatabase.class, REMINDER_DB_NAME).build();
    }

    @Provides
    @Singleton
    ReminderDao provideReminderDao(@NonNull ReminderDatabase db) {
        return db.reminderDao();
    }
}
