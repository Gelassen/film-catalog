package com.home.snappii.di;


import com.home.snappii.AppApplication;
import com.home.snappii.providers.DataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private AppApplication application;

    public MainModule(AppApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public DataProvider getProvider() {
        return new DataProvider(application);
    }
}
