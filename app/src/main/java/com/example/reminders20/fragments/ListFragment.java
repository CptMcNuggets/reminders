package com.example.reminders20.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminders20.AdapterCallback;
import com.example.reminders20.RemindersApplication;
import com.example.reminders20.viewModels.ListViewModel;
import com.example.reminders20.MainActivity;
import com.example.reminders20.R;
import com.example.reminders20.RemindersAdapter;
import com.example.reminders20.db.Reminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

public class ListFragment extends Fragment implements AdapterCallback {

    private RemindersAdapter adapter;

    @Inject
    ListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemindersApplication.getRootComponent().inject(this);
    }

    private void subscribeOnViewModel(View view){
        viewModel.getAllReminders().observe(getViewLifecycleOwner(), new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminderList) {
                Context context = getContext();
                if (context == null) return;
                adapter.updateItems(context, reminderList);
            }
        });
        viewModel.deletedReminder.observe(getViewLifecycleOwner(), new Observer<Reminder>() {
            @Override
            public void onChanged(Reminder reminder) {
            }
        });
    }
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
        adapter = new RemindersAdapter();
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
                Reminder reminder = (Reminder) adapter.list.get(position);
                adapter.list.remove(position);
                adapter.notifyItemRemoved(position);
                Snackbar.make(view, R.string.undo_deletion,Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo_deletion, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                switch (event) {
                                    case DISMISS_EVENT_ACTION:
                                        adapter.list.add(position,reminder);
                                        adapter.notifyItemInserted(position);
                                        break;
                                    case DISMISS_EVENT_TIMEOUT:
                                        viewModel.deleteReminderWithUndo(reminder);
                                }
                            }
                        })
                        .show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        fab_new_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.openNewReminderAction);
            }
        });
        subscribeOnViewModel(view);
    }

    @Override
    public void undoDeletion(int position) {
        adapter.notifyItemChanged(position);
    }
}
