package com.example.reminders20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.reminders20.MainActivity;
import com.example.reminders20.R;
import com.example.reminders20.RemindersApplication;
import com.example.reminders20.db.Reminder;
import com.example.reminders20.viewModels.NewReminderViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewReminderFragment extends Fragment {
    @Inject
    NewReminderViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemindersApplication.getRootComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) {
            return;
        }
        EditText inputTitle = view.findViewById(R.id.input_title);
        EditText inputDescription = view.findViewById(R.id.input_description);
        final long[] timestamp = {-1};

            timestamp[0] = NewReminderFragmentArgs.fromBundle(getArguments()).getTimestamp();
            if(timestamp[0] != 0) {
                viewModel.getReminderByTimestamp(timestamp[0]).observe(getViewLifecycleOwner(), new Observer<Reminder>() {
                    @Override
                    public void onChanged(Reminder reminder) {
                        inputTitle.setText(reminder.getTitle());
                        inputDescription.setText(reminder.getDescription());
                    }
                });
            }
        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            long newTimestamp;

            if (timestamp[0] > 0) {
                newTimestamp = timestamp[0];
            } else {
                newTimestamp = System.currentTimeMillis();
            }


            viewModel.insertNewReminder(new Reminder(inputTitle.getText().toString(), inputDescription.getText().toString(), newTimestamp));
        });
        viewModel.insertedReminder.observe(getViewLifecycleOwner(), reminder -> {
            Navigation.findNavController(view).popBackStack();
        });
    }
}
