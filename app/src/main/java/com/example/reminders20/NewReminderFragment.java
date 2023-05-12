package com.example.reminders20;

import android.app.Fragment;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewReminderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        MainActivity activity = (MainActivity) getActivity();
        Button saveButton = view.findViewById(R.id.save_button);
        EditText inputTitle = view.findViewById(R.id.input_title);
        EditText inputDescription = view.findViewById(R.id.input_description);
        if(bundle != null) {
           activity.reminderDao.getReminderByTimestamp(bundle.getLong("timestamp"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            result -> {
                                inputTitle.setText(result.getTitle());
                                inputDescription.setText(result.getDescription());
                            }, error -> {
                                error.printStackTrace();
                            }
                );
        }
        saveButton.setOnClickListener(v -> {
            long timestamp = System.currentTimeMillis();
            long argTimestamp = bundle.getLong("timestamp", -1L);
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
                            getFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

        });
    }
}
