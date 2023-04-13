package com.example.reminders20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {
    final int ITEM_REMINDER = 0;
    final int ITEM_DIVIDER = 1;
    private List<Items> list = new ArrayList<>();
    public void updateItems(List<Reminder> reminderList, List<Divider> dividerList) {
        this.list.clear();
        Collections.sort(reminderList, new Comparator<Reminder>() {
            @Override
            public int compare(Reminder o1, Reminder o2) {
                Long timestamp1 = o1.getDate().toInstant().toEpochMilli();
                Long timestamp2 = o2.getDate().toInstant().toEpochMilli();
                return timestamp1.compareTo(timestamp2);
            }
        });
        Date today = new Date();
        list.add(dividerList.get(0));
        for(int i = 0; i < reminderList.size(); i++) {
            if(Date.from(reminderList.get(i).getDate().toInstant()).compareTo(today) == 0) {
                this.list.add(reminderList.get(i));
            }
        }
        list.add(dividerList.get(1));
        for(int i = 0; i < reminderList.size(); i++) {
            if(Date.from(reminderList.get(i).getDate().toInstant()).compareTo(today) > 0) {
                this.list.add(reminderList.get(i));
            }
        }
        list.add(dividerList.get(2));
        for(int i = 0; i < reminderList.size(); i++) {
            if(Date.from(reminderList.get(i).getDate().toInstant()).compareTo(today) < 0) {
                this.list.add(reminderList.get(i));
            }
        }
        notifyDataSetChanged();
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
        switch (holder.getItemViewType()) {
            case ITEM_REMINDER:
                ReminderViewHolder holderReminder = (ReminderViewHolder) holder;
                Reminder itemReminder = (Reminder) list.get(position);
                Context context = holder.itemView.getContext();
                holderReminder.title.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_title), itemReminder.getTitle()));
                holderReminder.description.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_description), itemReminder.getDescription()));
                holderReminder.date.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), itemReminder.getDate()));
                break;
            case ITEM_DIVIDER:
                DividerViewHolder holderDivider = (DividerViewHolder) holder;
                Divider itemDivider = (Divider) list.get(position);
                holderDivider.divider.setText(itemDivider.getDividerName());
                break;
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
