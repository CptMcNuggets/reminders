package com.example.reminders20.di.modules;

import com.example.reminders20.db.ReminderDao;
import com.example.reminders20.viewModels.ListViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    @Singleton
    ListViewModel provideListViewModel(ReminderDao reminderDao) {
        return new ListViewModel(reminderDao);
    }

}
