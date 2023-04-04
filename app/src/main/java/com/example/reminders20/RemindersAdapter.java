package com.example.reminders20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {
    private List<Reminder> list = new ArrayList<>();
    public void updateReminders (List<Reminder> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
    @NonNull
    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder listItem = list.get(position);
        Context context = holder.itemView.getContext();
        if (listItem.getIsOverdue() == true) {
            holder.itemView.setBackgroundColor(context.getColor(R.color.red_alert));
        }
        holder.title.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_title), listItem.getTitle()));
        holder.description.setText(String.format("%s %s", context.getString(R.string.reminders_list_item_description), listItem.getDescription()));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
