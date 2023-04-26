package com.example.reminders20;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReminderDao{
    @Query("SELECT * FROM Reminder")
    public Single<List<Reminder>> getAll();
    @Insert
    public Completable insertReminder(Reminder reminder);
    @Delete
    public Completable deleteReminder(Reminder reminder);
}

