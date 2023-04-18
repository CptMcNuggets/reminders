package com.example.reminders20;

import androidx.annotation.NonNull;

import java.util.Date;

public class Reminder extends Items {
    private String title;
    private String description;
    private long date;
    public Reminder (String title, String description, long date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public long getTimestamp() {
        return date;
    }

    public Date getDate() {
        return new Date(date);
    }
    public void setDate(long date) {
        this.date = date;
    }
    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getDescription();
    }
}
