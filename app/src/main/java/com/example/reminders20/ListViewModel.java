package com.example.reminders20;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    private MutableLiveData<Reminder> _deletedReminder = new MutableLiveData<>();
    public LiveData<Reminder> deletedReminder = _deletedReminder;
    private ReminderDao reminderDao; // TODO init
    public LiveData<List<Reminder>> getAllReminders = reminderDao.getAll();
    public void deleteReminderWithUndo (Reminder reminder) {
        reminderDao.deleteReminder(reminder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _deletedReminder.setValue(reminder);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public void undoDeletionReminder(Reminder reminder) {
        reminderDao.insertReminder(reminder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
