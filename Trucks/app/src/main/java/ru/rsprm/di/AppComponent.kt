package ru.rsprm.di

import dagger.Component
import ru.rsprm.screens.add.AddTruckActivity
import ru.rsprm.screens.list.MainActivity

import javax.inject.Singleton

@Singleton
@Component(modules =[NetworkModule::class, Module::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: AddTruckActivity)
}
