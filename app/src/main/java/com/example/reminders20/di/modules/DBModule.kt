package com.example.reminders20.di.modules

import androidx.room.Room
import com.example.reminders20.RemindersApplication
import com.example.reminders20.db.ReminderDao
import com.example.reminders20.db.ReminderDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {
    @Provides
    @Singleton
    fun provideReminderDatabase(application: RemindersApplication?): ReminderDatabase {
        return Room.databaseBuilder(application!!, ReminderDatabase::class.java, REMINDER_DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(db: ReminderDatabase): ReminderDao? {
        return db.reminderDao()
    }

    companion object {
        const val REMINDER_DB_NAME = "reminder_db"
    }
}