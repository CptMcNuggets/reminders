package com.example.reminders20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewReminderFragment extends Fragment {
    private Disposable singleReminder = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) {
            return;
        }
        Button saveButton = view.findViewById(R.id.save_button);
        EditText inputTitle = view.findViewById(R.id.input_title);
        EditText inputDescription = view.findViewById(R.id.input_description);
        if(bundle != null) {
            singleReminder = activity.reminderDao.getReminderByTimestamp(bundle.getLong(Reminder.ARG_TIMESTAMP))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            result -> {
                                inputTitle.setText(result.getTitle());
                                inputDescription.setText(result.getDescription());
                            }, Throwable::printStackTrace
                );
        }
        saveButton.setOnClickListener(v -> {
            long timestamp = System.currentTimeMillis();

            long argTimestamp = 0;
            if (bundle != null) {
                argTimestamp = bundle.getLong(Reminder.ARG_TIMESTAMP, -1L);
            }
            if(argTimestamp > 0) {
                timestamp = argTimestamp;
            }
            activity.reminderDao.insertReminder(new Reminder(inputTitle.getText().toString(), inputDescription.getText().toString(), timestamp))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            activity.onBackPressed();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (singleReminder != null) {
            singleReminder.dispose();
        }
    }
}
