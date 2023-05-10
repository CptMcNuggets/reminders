package com.example.reminders20;

import android.os.Bundle;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Reminder extends Items {
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @PrimaryKey
    private long timestamp;
    public Reminder (String title, String description, long timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }
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
