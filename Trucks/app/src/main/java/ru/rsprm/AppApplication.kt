package ru.rsprm

import android.app.Application
import ru.rsprm.di.AppComponent
import ru.rsprm.di.DaggerAppComponent
import ru.rsprm.di.Module
import ru.rsprm.di.NetworkModule

class AppApplication : Application() {

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        component = initDagger()
    }

    fun getComponent(): AppComponent {
        return component!!
    }

    private fun initDagger(): AppComponent {
        return DaggerAppComponent
            .builder()
            .networkModule(NetworkModule(this, getString(R.string.url)))
            .module(Module(this))
            .build()
    }
}
