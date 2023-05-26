package com.example.reminders20;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeletionConfirmationDialogFragment extends DialogFragment {
    public static String TAG = "ReminderDeletionConfirmation";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity context = (MainActivity) getContext();
        Bundle bundle = this.getArguments();
        int position = bundle.getInt(RemindersAdapter.ARG_POSITION);
        return new AlertDialog.Builder(context)
                .setTitle(R.string.delete_reminder_alert_title)
                .setMessage(R.string.delete_reminder_alert_message)
                .setNegativeButton(R.string.reminder_delete_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.reminder_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.reminderDao.deleteReminder((Reminder) RemindersAdapter.list.get(position))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
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
                }).create();
    }
}
