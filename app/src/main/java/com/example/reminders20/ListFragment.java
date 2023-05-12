package com.example.reminders20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {

    private Disposable listDisposable = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                        results -> adapter.updateItems(activity, results), Throwable::printStackTrace
                );


        fab_new_reminder.setOnClickListener(v -> getChildFragmentManager().beginTransaction().replace(R.id.container, new NewReminderFragment(), "add_fragment").addToBackStack("add_fragment").commit());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listDisposable != null) {
            listDisposable.dispose();
        }
    }

}
