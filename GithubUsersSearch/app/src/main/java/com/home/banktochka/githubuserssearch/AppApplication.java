package com.home.banktochka.githubuserssearch;


import android.app.Application;
import android.support.multidex.MultiDex;

import com.home.banktochka.githubuserssearch.di.Component;
import com.home.banktochka.githubuserssearch.di.DaggerComponent;
import com.home.banktochka.githubuserssearch.di.NetworkModule;
import com.home.banktochka.githubuserssearch.di.UsersModule;

public class AppApplication extends Application implements IApplication {

    private Component component;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        component = DaggerComponent.builder()
                .networkModule(new NetworkModule(this))
                .usersModule(new UsersModule(this))
                .build();
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
