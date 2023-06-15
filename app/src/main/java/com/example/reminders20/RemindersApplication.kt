package com.example.reminders20

import android.app.Application
import com.example.reminders20.di.DaggerRemindersComponent
import com.example.reminders20.di.RemindersComponent

class RemindersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        rootComponent = DaggerRemindersComponent.builder().application(this).build()
    }

    companion object {
        lateinit var rootComponent: RemindersComponent
            private set
    }
}