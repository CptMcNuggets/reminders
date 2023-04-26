package com.example.reminders20;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) return;
        FloatingActionButton fab_new_reminder = view.findViewById(R.id.fab_new_reminder);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RemindersAdapter adapter = new RemindersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.updateItems(context, ((MainActivity) getActivity()).reminderDao.getAll());
        fab_new_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.container, new NewReminderFragment(), "add_fragment").addToBackStack("add_fragment").commit();
            }
        });
    }
}
