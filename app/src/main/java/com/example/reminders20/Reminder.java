package com.example.reminders20;

import androidx.annotation.NonNull;

public class Reminder {
    private String title;
    private String description;
    private boolean isOverdue;
    public Reminder (String title, String description) {
        this.title = title;
        this.description = description;
        isOverdue = Integer.parseInt(title) > 30;
    }
    public boolean IsOverdue() {
        return isOverdue;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getDescription();
    }
}
