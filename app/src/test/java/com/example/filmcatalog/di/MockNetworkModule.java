package com.example.filmcatalog.di;

import com.example.filmcatalog.Config;
import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.films.providers.FilmsProvider;
import com.example.filmcatalog.films.providers.NetworkService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class MockNetworkModule {

    private IApplication application;

    public MockNetworkModule(IApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    @Named("media")
    String providesCurrencyUrl() {
        return Config.API;
    }

    @Provides
    @Named("network")
    Scheduler providesSubscribeScheduler() {
        return Schedulers.trampoline();
    }

    @Provides
    @Named("ui")
    Scheduler providesObserveScheduler() {
        return Schedulers.trampoline();
    }

    @Provides
    FilmsProvider providesFilmsProvider() {
        return new FilmsProvider(application);
    }

    @Provides
    NetworkService providesNetworkService() {
        return new NetworkService(application);
    }
}
