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
import javax.inject.Inject

class NewReminderViewModel @Inject constructor(private val reminderDao: ReminderDao) : ViewModel() {
    private val _reminderUpdated = MutableLiveData<Reminder?>()
    var insertedReminder: LiveData<Reminder?> = _reminderUpdated
    fun insertNewReminder(reminder: Reminder?) {
        reminderDao.insertReminder(reminder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onComplete() {
                        _reminderUpdated.value = reminder
                    }

                    override fun onError(e: Throwable) {}
                })
    }

    fun getReminderByTimestamp(timestamp: Long): LiveData<Reminder?>? {
        return reminderDao.getReminderByTimestamp(timestamp)
    }
}