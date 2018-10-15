package com.home.banktochka.githubuserssearch;


import android.app.Application;
import android.support.multidex.MultiDex;

import com.home.banktochka.githubuserssearch.di.Component;

public class AppApplication extends Application implements IApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public Component getComponent() {
        return null;
    }
}
