package com.example.reminders20.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM Reminder ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Reminder>>

    @Query("SELECT * FROM Reminder WHERE timestamp = :timestamp")
    fun getReminderByTimestamp(timestamp: Long): LiveData<Reminder?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}