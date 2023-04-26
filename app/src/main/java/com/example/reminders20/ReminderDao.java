package com.example.reminders20;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReminderDao{
    @Query("SELECT * FROM Reminder")
    List<Reminder> getAll();
    @Insert
    Single<Reminder> insertReminder(Reminder reminder);
    @Delete
    Single<Reminder> deleteReminder(Reminder reminder);
}
