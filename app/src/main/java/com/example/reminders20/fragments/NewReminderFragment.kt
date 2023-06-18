package com.example.reminders20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.reminders20.R
import com.example.reminders20.RemindersApplication
import com.example.reminders20.db.Reminder
import com.example.reminders20.viewModels.NewReminderViewModel

class NewReminderFragment : Fragment() {

    //TODO inject with Koin
    lateinit var viewModel: NewReminderViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val inputTitle = view.findViewById<EditText>(R.id.input_title)
        val inputDescription = view.findViewById<EditText>(R.id.input_description)
        val timestamp = 0L //NewReminderFragmentArgs.fromBundle(requireArguments()).timestamp
        if (timestamp != 0L) {
            viewModel.getReminderByTimestamp(timestamp).observe(viewLifecycleOwner) { reminder ->
                inputTitle.setText(reminder?.title)
                inputDescription.setText(reminder?.description)
            }
        }
        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            val newTimestamp: Long = if (timestamp > 0) {
                timestamp
            } else {
                System.currentTimeMillis()
            }
            viewModel.insertNewReminder(
                Reminder(
                    inputTitle.text.toString(),
                    inputDescription.text.toString(),
                    newTimestamp
                )
            )
        }
        viewModel.insertedReminder.observe(viewLifecycleOwner) { findNavController(view).popBackStack() }
    }
}