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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        adapter.updateItems(getTestList(),getDividers());
        fab_reshuffle.setOnClickListener(v -> {
            adapter.updateItems(getTestList(),getDividers());
        });
    }
    public List<Divider> getDividers() {
        List<Divider> dividers = new ArrayList<>();
        dividers.add(new Divider("Today"));
        dividers.add(new Divider("Upcoming"));
        dividers.add(new Divider("Overdue"));
        return dividers;
    }
    public List<Reminder> getTestList() {
        Random rn = new Random();
        long min = -(1000 * 60 * 60 * 24 * 20);
        long max = 1000 * 60 * 60 * 24 * 20;
        List<Reminder> testList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int title = rn.nextInt(100);
            int description = rn.nextInt(100);
            long randomDays = ThreadLocalRandom.current().nextLong(min, max);
            long date = System.currentTimeMillis() + randomDays;
            testList.add(new Reminder(Integer.toString(title), Integer.toString(description), date));
        }
        return testList;
    }
}
