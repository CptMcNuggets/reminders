package com.example.reminders20.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminders20.Items
import java.util.*

@Entity
 data class Reminder(
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "description") val description: String,
        @PrimaryKey val timestamp: Long) : Items() {

    val date: Date
        get() = Date(timestamp)

    companion object {
        const val ARG_TIMESTAMP = "timestamp"
    }
}