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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment{

    private Disposable listDisposable = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;
        FloatingActionButton fab_new_reminder = view.findViewById(R.id.fab_new_reminder);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RemindersAdapter adapter = new RemindersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        listDisposable = activity.reminderDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        results -> {
                            adapter.updateItems(activity, results);
                        }, error -> {
                            error.printStackTrace();
                        }
                );


        fab_new_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.container, new NewReminderFragment(), "add_fragment").addToBackStack("add_fragment").commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listDisposable != null) {
            listDisposable.dispose();
        }
    }

}
