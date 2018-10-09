package com.home.snappii;


import android.app.Application;
import android.support.multidex.MultiDex;

import com.home.snappii.di.Component;
import com.home.snappii.di.DaggerComponent;
import com.home.snappii.di.MainModule;
import com.home.snappii.di.NetworkModule;

public class AppApplication extends Application {

    private Component component;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        component = DaggerComponent.builder()
                .networkModule(new NetworkModule(this))
                .mainModule(new MainModule(this))
                .build();
    }

    public Component getComponent() {
        return component;
    }
}
