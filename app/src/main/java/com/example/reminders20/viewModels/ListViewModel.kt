package com.example.reminders20.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reminders20.db.Reminder
import com.example.reminders20.db.ReminderDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ListViewModel constructor(/*TODO inject with Koin*/private val reminderDao: ReminderDao) : ViewModel() {
    private val _deletedReminder = MutableLiveData<Reminder>()
    var deletedReminder: LiveData<Reminder> = _deletedReminder
    val allReminders: LiveData<List<Reminder>> = reminderDao.getAll()

    fun deleteReminderWithUndo(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onComplete() {
                        _deletedReminder.value = reminder
                    }

                    override fun onError(e: Throwable) {}
                })
    }
}