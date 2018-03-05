package com.example.filmcatalog;


import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.filmcatalog.di.Component;
import com.example.filmcatalog.di.DaggerComponent;
import com.example.filmcatalog.di.IComponent;
import com.example.filmcatalog.di.NetworkModule;

public class App extends Application implements IApplication {

    public static final String TAG = "TAG";

    private static IApplication application;

    private Component component;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        component = DaggerComponent.builder()
                .networkModule(new NetworkModule(this))
                .build();
        application = this;
    }

    @Override
    public IComponent getComponent() {
        return component;
    }
}
