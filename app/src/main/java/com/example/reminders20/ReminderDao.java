package com.example.reminders20;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReminderDao{
    @Query("SELECT * FROM Reminder")
    Flowable<List<Reminder>> getAll();
    @Query("SELECT * FROM Reminder WHERE timestamp = :timestamp")
    Single<Reminder> getReminderByTimestamp(long timestamp);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertReminder(Reminder reminder);
    @Delete
    Completable deleteReminder(Reminder reminder);
}

