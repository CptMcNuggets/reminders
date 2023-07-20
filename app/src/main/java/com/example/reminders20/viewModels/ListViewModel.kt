package com.example.reminders20.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminders20.db.Reminder
import com.example.reminders20.db.ReminderDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ListViewModel constructor(private val reminderDao: ReminderDao) : ViewModel() {
    private val _deletedReminder = MutableLiveData<Reminder>()
    var deletedReminder: LiveData<Reminder> = _deletedReminder
    //val allReminders: LiveData<List<Reminder>> = reminderDao.getAll()

    val allRemindersFlow: Flow<List<Reminder>> = reminderDao.getAll()
    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.deleteReminder(reminder)
            _deletedReminder.value = reminder
        }
    }
}