package com.example.reminders20.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.reminders20.Items;

import java.util.Date;

@Entity
public class Reminder extends Items {
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "description")
    private final String description;
    @PrimaryKey
    private final long timestamp;
    public Reminder (String title, String description, long timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }
    public static final String ARG_TIMESTAMP = "timestamp";
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDate() {
        return new Date(timestamp);
    }
    public long getTimestamp() {
        return timestamp;
    }

}
