package com.example.reminders20;

import androidx.annotation.NonNull;

import java.time.ZonedDateTime;

public class Reminder extends Items {
    private String title;
    private String description;
    private ZonedDateTime date;
    public Reminder (String title, String description, ZonedDateTime date) {
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
    public ZonedDateTime getDate() {
        return date;
    }
    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getDescription();
    }
}
