package com.example.reminders20;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reminder)) return false;
        Reminder reminder = (Reminder) o;
        return timestamp == reminder.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, timestamp);
    }
}
