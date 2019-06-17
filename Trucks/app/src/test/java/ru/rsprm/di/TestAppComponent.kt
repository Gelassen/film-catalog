package ru.rsprm.di

import dagger.Component
import ru.rsprm.AddTruckPresenterTest
import ru.rsprm.BaseTest
import ru.rsprm.MainPresenterTest

import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, Module::class])
interface TestAppComponent {

    fun inject(mainPresenterTest: MainPresenterTest)
    fun inject(baseTest: BaseTest)
    fun inject(host: AddTruckPresenterTest)

}
