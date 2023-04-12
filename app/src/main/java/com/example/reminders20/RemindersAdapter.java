package com.example.reminders20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {
    final int ITEM_REMINDER = 0;
    final int ITEM_DIVIDER = 1;
    private List<Reminder> list = new ArrayList<>();
    private List<Divider> dividers = new ArrayList<>();

    public void updateReminders(List<Reminder> list) {
        this.list = list;
        Collections.sort(list, new Comparator<Reminder>() {
            @Override
            public int compare(Reminder o1, Reminder o2) {
                Long timestamp1 = o1.getDate().toInstant().toEpochMilli();
                Long timestamp2 = o2.getDate().toInstant().toEpochMilli();
                return timestamp1.compareTo(timestamp2);
            }
        });
        notifyDataSetChanged();
    }
    public void updateDividers(List<Divider> dividers) {
        this.dividers = dividers;
    }
    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class ReminderViewHolder extends RemindersAdapter.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }

    public class DividerViewHolder extends RemindersAdapter.ViewHolder {
        TextView divider;
        public DividerViewHolder(@NonNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
        }
    }
    @NonNull
    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_REMINDER:
                View reminderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
                return new ReminderViewHolder(reminderView);
            case ITEM_DIVIDER:
                View dividerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider, parent, false);
                return new DividerViewHolder(dividerView);
            default: return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RemindersAdapter.ViewHolder holder, int position) {
        if(getItemViewType(position) == ITEM_REMINDER) {
            ReminderViewHolder holderReminder = (ReminderViewHolder) holder;
            Reminder listItem = list.get(position);
            Context context = holder.itemView.getContext();
            holderReminder.title.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_title), listItem.getTitle()));
            holderReminder.description.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_description), listItem.getDescription()));
            holderReminder.date.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), listItem.getDate()));
        } else {
            DividerViewHolder holderDivider = (DividerViewHolder) holder;
            Divider divider = dividers.get(position);
            //Context context = holder.itemView.getContext();
            holderDivider.divider.setText(String.format(divider.getDividerName()));
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Reminder) {
            return ITEM_REMINDER;
        }
        else return ITEM_DIVIDER;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
