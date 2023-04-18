package com.example.reminders20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
                Long timestamp1 = o1.getDate();
                Long timestamp2 = o2.getDate();
                return timestamp1.compareTo(timestamp2);
            }
        });
        Calendar todayDate = Calendar.getInstance();
        Calendar reminderDate = Calendar.getInstance();
        List<Items> todayList = new ArrayList<>();
        List<Items> overdueList = new ArrayList<>();
        List<Items> upcomingList = new ArrayList<>();
        for(int i = 0; i < reminderList.size(); i++) {
            reminderDate.setTimeInMillis(reminderList.get(i).getDate());
            if(reminderDate.get(Calendar.DAY_OF_YEAR) > todayDate.get(Calendar.DAY_OF_YEAR) ||
            reminderDate.get(Calendar.YEAR) > todayDate.get(Calendar.YEAR)) {
                upcomingList.add(reminderList.get(i));
            }
            else if(reminderDate.get(Calendar.DAY_OF_YEAR) < todayDate.get(Calendar.DAY_OF_YEAR) ||
                    reminderDate.get(Calendar.YEAR) < todayDate.get(Calendar.YEAR)) {
                overdueList.add(reminderList.get(i));
            }
            else todayList.add(reminderList.get(i));
        }
        if (todayList.size() > 0) {
            todayList.add(0, dividerList.get(0));
        }
        if(upcomingList.size() > 0) {
            upcomingList.add(0, dividerList.get(1));
        }
        if(overdueList.size() > 0) {
            overdueList.add(0,dividerList.get(2));
        }
        list.addAll(todayList);
        list.addAll(upcomingList);
        list.addAll(overdueList);
        notifyDataSetChanged();
    }
    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        abstract void updateUI(int position);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class ReminderViewHolder extends RemindersAdapter.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        @Override
        void updateUI(int position) {
            Reminder itemReminder = (Reminder) list.get(position);
            Context context = itemView.getContext();
            title.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_title), itemReminder.getTitle()));
            description.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_description), itemReminder.getDescription()));
            date.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), new Date(itemReminder.getDate())));

        }

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }

    public class DividerViewHolder extends RemindersAdapter.ViewHolder {
        TextView divider;

        @Override
        void updateUI(int position) {
            Divider itemDivider = (Divider) list.get(position);
            divider.setText(itemDivider.getDividerName());
        }

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
        holder.updateUI(position);
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
