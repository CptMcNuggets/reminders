package com.example.reminders20;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminders20.db.Reminder;
import com.example.reminders20.fragments.ListFragmentDirections;
import com.example.reminders20.fragments.NewReminderFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {
    public final int ITEM_REMINDER = 0;
    public final int ITEM_DIVIDER = 1;
    public final List<Items> list = new ArrayList<>();
    public void updateItems(Context context, List<Reminder> reminderList) {
        list.clear();
        if (reminderList.size() == 0) {
            notifyDataSetChanged();
            return;
        }
        Collections.sort(reminderList, (o1, o2) -> {
            Date timestamp1 = o1.getDate();
            Date timestamp2 = o2.getDate();
            return timestamp2.compareTo(timestamp1);
        });
        Calendar calendar = Calendar.getInstance();
        Calendar currentDayCalendar = Calendar.getInstance();

        int todayStartIndex = -1;
        int todayEndIndex = reminderList.size() - 1;

        for (int i = 0; i < reminderList.size(); i++) {
            Reminder reminder = reminderList.get(i);
            calendar.setTime(reminder.getDate());
            if (calendar.get(Calendar.DAY_OF_YEAR) == currentDayCalendar.get(Calendar.DAY_OF_YEAR)
                    && calendar.get(Calendar.YEAR) == currentDayCalendar.get(Calendar.YEAR)) {
                if (todayStartIndex < 0) {
                    todayStartIndex = i;
                }
                todayEndIndex = i + 1;
            }
        }

        boolean hasTodayReminders = todayStartIndex >= 0;
        boolean hasTomorrowReminders = todayStartIndex > 0;
        boolean hasYesterdayReminders = todayEndIndex < reminderList.size();

        if (hasTodayReminders) {
            list.add(new Divider(context.getString(R.string.divider_today)));
            list.addAll(reminderList.subList(todayStartIndex, todayEndIndex));
        }

        if (hasTomorrowReminders) {
            list.add(new Divider(context.getString(R.string.divider_tomorrow)));
            list.addAll(reminderList.subList(0, todayStartIndex));
        }

        if (hasYesterdayReminders) {
            list.add(new Divider(context.getString(R.string.divider_yesterday)));
            list.addAll(reminderList.subList(todayEndIndex, reminderList.size()));
        }

        notifyDataSetChanged();
    }
    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void updateUI(int position);
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
            MainActivity activity = (MainActivity) itemView.getContext();
            itemView.setOnClickListener(v -> {
                NewReminderFragment editReminder = new NewReminderFragment();
                Reminder reminder = (Reminder) list.get(getAdapterPosition());
                long timestamp = reminder.getTimestamp();
                Navigation.findNavController(itemView).navigate(ListFragmentDirections.openNewReminderAction().setTimestamp(timestamp));
            });
        }

        @Override
        void updateUI(int position) {
            Reminder itemReminder = (Reminder) list.get(position);
            Context context = itemView.getContext();
            title.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_title), itemReminder.getTitle()));
            description.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_description), itemReminder.getDescription()));
            date.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), itemReminder.getDate()));
        }
    }

    public class DividerViewHolder extends RemindersAdapter.ViewHolder {
        TextView divider;

        public DividerViewHolder(@NonNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
        }

        @Override
        void updateUI(int position) {
            Divider itemDivider = (Divider) list.get(position);
            divider.setText(itemDivider.getDividerName());
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
            default:
                return null;
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
        } else return ITEM_DIVIDER;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
