package com.example.reminders20;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReminderDao{
    @Query("SELECT * FROM Reminder")
    public Flowable<List<Reminder>> getAll();
    @Query("SELECT * FROM Reminder WHERE timestamp = :timestamp")
    public  Single<Reminder> getReminderByTimestamp(long timestamp);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public Completable insertReminder(Reminder reminder);
    @Delete
    public Completable deleteReminder(Reminder reminder);
}

