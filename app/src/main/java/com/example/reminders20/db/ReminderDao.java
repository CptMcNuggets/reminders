package com.example.reminders20.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface ReminderDao{
    @Query("SELECT * FROM Reminder")
    LiveData<List<Reminder>> getAll();
    @Query("SELECT * FROM Reminder WHERE timestamp = :timestamp")
    LiveData<Reminder> getReminderByTimestamp(long timestamp);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertReminder(Reminder reminder);
    @Delete
    Completable deleteReminder(Reminder reminder);
}

