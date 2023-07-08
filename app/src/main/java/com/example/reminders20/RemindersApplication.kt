package com.example.reminders20

import android.app.Application
import androidx.room.Room
import com.example.reminders20.db.ReminderDatabase
import com.example.reminders20.viewModels.ListViewModel
import com.example.reminders20.viewModels.NewReminderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RemindersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val daoModule = module {
            single {
                Room.databaseBuilder(get(), ReminderDatabase::class.java, "reminder_db")
                    .build()
                    .reminderDao()
            }
            single {  }
        }
        val viewModelModule = module {
            viewModel { ListViewModel(get()) }
            viewModel { NewReminderViewModel(get()) }
        }
        startKoin {
            androidLogger()
            androidContext(this@RemindersApplication)
            modules(daoModule, viewModelModule)
        }
    }
}