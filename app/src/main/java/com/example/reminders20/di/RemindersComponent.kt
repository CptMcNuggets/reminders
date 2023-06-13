package com.example.reminders20.di

import com.example.reminders20.RemindersApplication
import com.example.reminders20.di.modules.DBModule
import com.example.reminders20.di.modules.ViewModelModule
import com.example.reminders20.fragments.ListFragment
import com.example.reminders20.fragments.NewReminderFragment
import com.example.reminders20.viewModels.ListViewModel
import com.example.reminders20.viewModels.NewReminderViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/*
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 * */
@Component(modules = [ViewModelModule::class, DBModule::class])
@Singleton
interface RemindersComponent {
    /* We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: RemindersApplication?): Builder?
        fun build(): RemindersComponent?
    }

    fun inject(listViewModel: ListViewModel?)
    fun inject(listFragment: ListFragment?)
    fun inject(newReminderViewModel: NewReminderViewModel?)
    fun inject(newReminderFragment: NewReminderFragment?)
}