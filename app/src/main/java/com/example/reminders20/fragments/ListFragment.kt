package com.example.reminders20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reminders20.AdapterCallback
import com.example.reminders20.MainActivity
import com.example.reminders20.R
import com.example.reminders20.RemindersAdapter
import com.example.reminders20.RemindersAdapter.Companion.ITEM_REMINDER
import com.example.reminders20.RemindersApplication
import com.example.reminders20.db.Reminder
import com.example.reminders20.viewModels.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class ListFragment : Fragment(), AdapterCallback {

    private lateinit var adapter: RemindersAdapter

    val viewModel: ListViewModel by viewModel()

    private fun subscribeOnViewModel() {
        viewModel.allReminders.observe(viewLifecycleOwner, Observer { reminderList ->
            val context = context ?: return@Observer
            adapter.updateItems(context, reminderList)
        })
        viewModel.deletedReminder.observe(viewLifecycleOwner) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity? ?: return

        adapter = RemindersAdapter()
        val fabNewReminder = view.findViewById<FloatingActionButton>(R.id.fab_new_reminder)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    return if (viewHolder.itemViewType != ITEM_REMINDER) {
                        0
                    } else super.getSwipeDirs(recyclerView, viewHolder)
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val reminder = adapter.list[position] as Reminder
                    adapter.list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    Snackbar.make(view, R.string.undo_deletion, Snackbar.LENGTH_SHORT)
                        .apply {
                            setAction(R.string.undo_deletion) {
                                this.dismiss()
                            }.addCallback(object : BaseCallback<Snackbar>() {
                                override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                                    when (event) {
                                        DISMISS_EVENT_ACTION -> {
                                            adapter.list.add(position, reminder)
                                            adapter.notifyItemInserted(position)
                                        }

                                        DISMISS_EVENT_TIMEOUT -> viewModel.deleteReminder(reminder)
                                        else -> {
                                            //ignore
                                        }
                                    }
                                }
                            })
                        }.show()
                        /*.also { snackBar ->
                            snackBar.setAction(R.string.undo_deletion) {
                                snackBar.dismiss()
                            }.addCallback(object : BaseCallback<Snackbar?>() {
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    when (event) {
                                        DISMISS_EVENT_ACTION -> {
                                            adapter.list.add(position, reminder)
                                            adapter.notifyItemInserted(position)
                                        }

                                        DISMISS_EVENT_TIMEOUT -> viewModel.deleteReminderWithUndo(reminder)
                                        else -> {
                                            //ignore
                                        }
                                    }
                                }
                            })
                        }*/
                        /*.run {
                            this
                        }
                        ?.let { snackbar ->
                           snackbar
                        }*/
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        fabNewReminder.setOnClickListener { findNavController(view).navigate(R.id.openNewReminderAction) }
        subscribeOnViewModel()
    }

    override fun undoDeletion(position: Int) {
        adapter.notifyItemChanged(position)
    }
}