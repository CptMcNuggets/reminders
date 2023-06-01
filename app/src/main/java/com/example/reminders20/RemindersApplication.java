package com.example.reminders20;

import android.app.Application;

import com.example.reminders20.di.DaggerRemindersComponent;
import com.example.reminders20.di.RemindersComponent;

public class RemindersApplication extends Application {

    private static RemindersComponent rootComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        rootComponent = DaggerRemindersComponent.builder().application(this).build();
    }

    public static RemindersComponent getRootComponent() {
        return rootComponent;
    }
}
