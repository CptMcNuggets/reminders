package com.example.reminders20;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewReminderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button saveButton = view.findViewById(R.id.save_button);
        EditText inputTitle = view.findViewById(R.id.input_title);
        EditText inputDescription = view.findViewById(R.id.input_description);
        saveButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).reminderDao.insertReminder(new Reminder(inputTitle.getText().toString(), inputDescription.getText().toString(), System.currentTimeMillis()));
            getFragmentManager().popBackStack();
        });
    }
}
