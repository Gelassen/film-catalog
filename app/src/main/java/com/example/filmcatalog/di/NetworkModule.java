package com.example.filmcatalog.di;

import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.films.providers.NetworkService;

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
    @Named("media")
    String providesMediaUrl() {
        return "https://developers.themoviedb.org/3/";
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

    @Provides
    NetworkService providesNetworkService() {
        return new NetworkService(application);
    }

}
