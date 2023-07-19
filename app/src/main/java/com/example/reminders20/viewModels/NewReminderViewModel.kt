package com.example.reminders20.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminders20.db.Reminder
import com.example.reminders20.db.ReminderDao
import kotlinx.coroutines.launch

class NewReminderViewModel constructor(private val reminderDao: ReminderDao) : ViewModel() {
    private val _reminderUpdated = MutableLiveData<Reminder?>()
    var insertedReminder: LiveData<Reminder?> = _reminderUpdated
    fun insertNewReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.insertReminder(reminder)
            _reminderUpdated.value = reminder
        }
    }

    fun getReminderByTimestamp(timestamp: Long): LiveData<Reminder?> {
        return reminderDao.getReminderByTimestamp(timestamp)
    }
}