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
import java.util.Calendar

class RemindersAdapter : RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {
    companion object {
        const val ITEM_REMINDER = 0
        private const val ITEM_DIVIDER = 1
    }
    val list: MutableList<Items> = ArrayList()
    fun updateItems(context: Context, reminderList: List<Reminder>) {
        list.clear()
        if (reminderList.isEmpty()) {
            notifyDataSetChanged()
            return
        }
        val sortedList = reminderList.sortedByDescending { it.timestamp }
        val calendar = Calendar.getInstance()
        val currentDayCalendar = Calendar.getInstance()
        var todayStartIndex = -1
        var todayEndIndex = sortedList.lastIndex

        sortedList.forEachIndexed { index, reminder ->
            calendar.time = reminder.date
            if (calendar[Calendar.DAY_OF_YEAR] == currentDayCalendar[Calendar.DAY_OF_YEAR]
                && calendar[Calendar.YEAR] == currentDayCalendar[Calendar.YEAR]) {
                if (todayStartIndex < 0) {
                    todayStartIndex = index
                }
                todayEndIndex = index + 1
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
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val date: TextView = itemView.findViewById(R.id.date)

        init {
            itemView.setOnClickListener {
                val reminder = list[adapterPosition] as Reminder
                val timestamp = reminder.timestamp
                findNavController(itemView).navigate(ListFragmentDirections.openNewReminderAction().setTimestamp(timestamp))
            }
        }

        override fun updateUI(position: Int) {
            val itemReminder = list[position] as Reminder
            val context = itemView.context
            title.text = String.format("%s %s", context.getString(R.string.reminders_list_item_title), itemReminder.title)
            description.text = String.format("%s %s", context.getString(R.string.reminders_list_item_description), itemReminder.description)
            date.text = String.format("%s %s", context.getString(R.string.reminders_list_item_timestamp), itemReminder.date)
        }
    }

    inner class DividerViewHolder(itemView: View) : ViewHolder(itemView) {
        private val divider: TextView = itemView.findViewById(R.id.divider)

        override fun updateUI(position: Int) {
            val itemDivider = list[position] as Divider
            divider.text = itemDivider.dividerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_REMINDER -> {
                val reminderView = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
                ReminderViewHolder(reminderView)
            }
            else -> {
                val dividerView = LayoutInflater.from(parent.context).inflate(R.layout.item_divider, parent, false)
                DividerViewHolder(dividerView)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUI(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Reminder) {
            ITEM_REMINDER
        } else {
            ITEM_DIVIDER
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}