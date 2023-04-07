package com.example.reminders20;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

public class Reminder {
    private String title;
    private String description;
    private LocalDateTime date;
    public Reminder (String title, String description, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getDescription();
    }
}
