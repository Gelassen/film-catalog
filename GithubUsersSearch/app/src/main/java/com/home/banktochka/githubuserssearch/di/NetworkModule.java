package com.home.banktochka.githubuserssearch.di;


import com.home.banktochka.githubuserssearch.IApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class NetworkModule {

    private IApplication application;

    public NetworkModule(IApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    String providesUrl() {
        return "https://api.github.com/";
    }

    @Provides
    @Named("network")
    Scheduler providesSubscribeScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("ui")
    Scheduler providesObserveScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
