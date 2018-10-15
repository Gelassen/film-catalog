package com.home.banktochka.githubuserssearch.di;

import com.home.banktochka.githubuserssearch.IApplication;
import com.home.banktochka.githubuserssearch.users.model.UsersModel;
import com.home.banktochka.githubuserssearch.users.providers.UsersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {

    private IApplication application;

    public UsersModule(IApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public UsersProvider providesUserProvider() {
        return new UsersProvider(application);
    }

    @Singleton
    @Provides
    public UsersModel providesUserModel() {
        return new UsersModel();
    }
}
