package com.example.filmcatalog;


import android.app.Application;

import com.example.filmcatalog.di.DaggerMockComponent;
import com.example.filmcatalog.di.IComponent;
import com.example.filmcatalog.di.MockComponent;
import com.example.filmcatalog.di.MockNetworkModule;

public class TestApp extends Application implements IApplication {

    private MockComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMockComponent.builder()
                .mockNetworkModule(new MockNetworkModule(this))
                .build();
    }

    @Override
    public IComponent getComponent() {
        return component;
    }
}
