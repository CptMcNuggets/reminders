package com.example.reminders20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.reminders20.db.Reminder
import com.example.reminders20.fragments.ListFragmentDirections
import com.example.reminders20.fragments.NewReminderFragment
import java.util.*

class RemindersAdapter : RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {
    val ITEM_REMINDER = 0
    val ITEM_DIVIDER = 1
    val list: MutableList<Items?> = ArrayList()
    fun updateItems(context: Context, reminderList: List<Reminder?>) {
        list.clear()
        if (reminderList.size == 0) {
            notifyDataSetChanged()
            return
        }
        Collections.sort(reminderList) { o1: Reminder?, o2: Reminder? ->
            val timestamp1 = o1?.date
            val timestamp2 = o2?.date
            timestamp2!!.compareTo(timestamp1)
        }
        val calendar = Calendar.getInstance()
        val currentDayCalendar = Calendar.getInstance()
        var todayStartIndex = -1
        var todayEndIndex = reminderList.size - 1
        for (i in reminderList.indices) {
            val reminder = reminderList[i]
            calendar.time = reminder?.date
            if (calendar[Calendar.DAY_OF_YEAR] == currentDayCalendar[Calendar.DAY_OF_YEAR]
                    && calendar[Calendar.YEAR] == currentDayCalendar[Calendar.YEAR]) {
                if (todayStartIndex < 0) {
                    todayStartIndex = i
                }
                todayEndIndex = i + 1
            }
        }
        val hasTodayReminders = todayStartIndex >= 0
        val hasTomorrowReminders = todayStartIndex > 0
        val hasYesterdayReminders = todayEndIndex < reminderList.size
        if (hasTodayReminders) {
            list.add(Divider(context.getString(R.string.divider_today)))
            list.addAll(reminderList.subList(todayStartIndex, todayEndIndex))
        }
        if (hasTomorrowReminders) {
            list.add(Divider(context.getString(R.string.divider_tomorrow)))
            list.addAll(reminderList.subList(0, todayStartIndex))
        }
        if (hasYesterdayReminders) {
            list.add(Divider(context.getString(R.string.divider_yesterday)))
            list.addAll(reminderList.subList(todayEndIndex, reminderList.size))
        }
        notifyDataSetChanged()
    }

    abstract inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun updateUI(position: Int)
    }

    inner class ReminderViewHolder(itemView: View) : ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        var date: TextView

        init {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            date = itemView.findViewById(R.id.date)
            val activity = itemView.context as MainActivity
            itemView.setOnClickListener { v: View? ->
                val editReminder = NewReminderFragment()
                val reminder = list[adapterPosition] as Reminder?
                val timestamp = reminder.getTimestamp()
                findNavController(itemView).navigate(ListFragmentDirections.openNewReminderAction().setTimestamp(timestamp))
            }
        }

        override fun updateUI(position: Int) {
            val itemReminder = list[position] as Reminder?
            val context = itemView.context
            description.text = String.format("%s %s", context.getString(R.string.reminders_list_item_description), itemReminder.getDescription())
            date.text = String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), itemReminder.getDate())
        }
    }

    inner class DividerViewHolder(itemView: View) : ViewHolder(itemView) {
        var divider: TextView

        init {
            divider = itemView.findViewById(R.id.divider)
        }

        override fun updateUI(position: Int) {
            val itemDivider = list[position] as Divider?
            divider.text = itemDivider.getDividerName()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_REMINDER -> {
                val reminderView = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
                ReminderViewHolder(reminderView)
            }
            ITEM_DIVIDER -> {
                val dividerView = LayoutInflater.from(parent.context).inflate(R.layout.item_divider, parent, false)
                DividerViewHolder(dividerView)
            }
            else -> null
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUI(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Reminder) {
            ITEM_REMINDER
        } else ITEM_DIVIDER
    }

    override fun getItemCount(): Int {
        return list.size
    }
}