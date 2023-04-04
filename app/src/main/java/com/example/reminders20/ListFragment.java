package com.example.reminders20;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab_reshuffle = view.findViewById(R.id.fab_reshuffle);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RemindersAdapter adapter = new RemindersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.updateReminders(getTestList());
        fab_reshuffle.setOnClickListener(v -> {
            adapter.updateReminders(getTestList());
        });
    }
    public List<Reminder> getTestList() {
        Random rn = new Random();
        List<Reminder> testList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int title = rn.nextInt(100);
            int description = rn.nextInt(100);
            testList.add(new Reminder(Integer.toString(title), Integer.toString(description)));
        }
        return testList;
    }
}
