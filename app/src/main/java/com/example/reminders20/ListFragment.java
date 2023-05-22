package com.example.reminders20;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Pair;

public class ListFragment extends Fragment implements AdapterCallback{

    private Disposable listDisposable = null;
    private static final int THREE_SECONDS_IN_MILLIS = 3000;
    private RemindersAdapter adapter;

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
        adapter = new RemindersAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if(viewHolder.getItemViewType() != adapter.ITEM_REMINDER) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Reminder deletedReminder = (Reminder) adapter.getList().get(position);
                CountDownTimer countDownTimer = new CountDownTimer(THREE_SECONDS_IN_MILLIS, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        activity.reminderDao.deleteReminder(deletedReminder)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        adapter.deletedReminders.remove(deletedReminder);
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                    }
                                });
                    }
                };
                adapter.deletedReminders.add(new Pair<>(deletedReminder,countDownTimer));
                adapter.notifyItemChanged(position);
                countDownTimer.start();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        listDisposable = activity.reminderDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        results -> adapter.updateItems(activity, results), Throwable::printStackTrace
                );

        fab_new_reminder.setOnClickListener(v -> activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewReminderFragment(), "add_fragment").addToBackStack("add_fragment").commit());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listDisposable != null) {
            listDisposable.dispose();
        }
    }

    @Override
    public void undoDeletion(int position) {
        adapter.notifyItemChanged(position);
    }
}
