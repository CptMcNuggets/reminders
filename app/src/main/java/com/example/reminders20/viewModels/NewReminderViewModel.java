package com.example.reminders20.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reminders20.MainActivity;
import com.example.reminders20.db.Reminder;
import com.example.reminders20.db.ReminderDao;
import com.example.reminders20.fragments.NewReminderFragment;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewReminderViewModel extends ViewModel {
    private MutableLiveData<Reminder> _reminderUpdated = new MutableLiveData<>();
    public LiveData<Reminder> insertedReminder = _reminderUpdated;
    private ReminderDao reminderDao;
    @Inject
    public NewReminderViewModel(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
    }
    public void insertNewReminder(Reminder reminder) {
       reminderDao.insertReminder(reminder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _reminderUpdated.setValue(reminder);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        }
    public LiveData<Reminder> getReminderByTimestamp(long timestamp) {
        return reminderDao.getReminderByTimestamp(timestamp);
    }

}
