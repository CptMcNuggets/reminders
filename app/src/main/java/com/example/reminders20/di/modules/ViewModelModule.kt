package com.example.reminders20.di.modules

import com.example.reminders20.db.ReminderDao
import com.example.reminders20.viewModels.ListViewModel
import com.example.reminders20.viewModels.NewReminderViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideListViewModel(reminderDao: ReminderDao): ListViewModel {
        return ListViewModel(reminderDao)
    }

    @Provides
    fun provideNewReminderViewModel(reminderDao: ReminderDao): NewReminderViewModel {
        return NewReminderViewModel(reminderDao)
    }
}