package com.example.reminders20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.reminders20.MainActivity
import com.example.reminders20.R
import com.example.reminders20.RemindersApplication
import com.example.reminders20.db.Reminder
import com.example.reminders20.viewModels.NewReminderViewModel
import javax.inject.Inject

class NewReminderFragment : Fragment() {
    @JvmField
    @Inject
    var viewModel: NewReminderViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RemindersApplication.Companion.getRootComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity? ?: return
        val inputTitle = view.findViewById<EditText>(R.id.input_title)
        val inputDescription = view.findViewById<EditText>(R.id.input_description)
        val timestamp = longArrayOf(-1)
        timestamp[0] = NewReminderFragmentArgs.fromBundle(requireArguments()).timestamp
        if (timestamp[0] != 0L) {
            viewModel!!.getReminderByTimestamp(timestamp[0])!!.observe(viewLifecycleOwner) { reminder ->
                    inputTitle.setText(reminder?.title)
                    inputDescription.setText(reminder?.description)
            }
        }
        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener { v: View? ->
            val newTimestamp: Long
            newTimestamp = if (timestamp[0] > 0) {
                timestamp[0]
            } else {
                System.currentTimeMillis()
            }
            viewModel!!.insertNewReminder(Reminder(inputTitle.text.toString(), inputDescription.text.toString(), newTimestamp))
        }
        viewModel!!.insertedReminder.observe(viewLifecycleOwner) { reminder: Reminder? -> findNavController(view).popBackStack() }
    }
}