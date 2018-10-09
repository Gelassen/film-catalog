package com.home.snappii.di;

import android.app.Application;

import com.home.snappii.AppApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class NetworkModule {


    private Application application;

    public NetworkModule(AppApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    @Named("media")
    String providesMediaUrl() {
        return "https://randomuser.me/";
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
