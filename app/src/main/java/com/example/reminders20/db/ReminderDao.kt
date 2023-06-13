package com.example.reminders20.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.rxjava3.core.Completable

@Dao
interface ReminderDao {
    @get:Query("SELECT * FROM Reminder")
    val all: LiveData<List<Reminder?>?>?

    @Query("SELECT * FROM Reminder WHERE timestamp = :timestamp")
    fun getReminderByTimestamp(timestamp: Long): LiveData<Reminder?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder?): Completable

    @Delete
    fun deleteReminder(reminder: Reminder?): Completable
}