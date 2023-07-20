package com.example.reminders20

import com.example.reminders20.db.Reminder

interface AdapterCallback {
    fun onItemClick(reminder: Reminder)
}