package com.home.snappii.di;

import com.home.snappii.providers.DataProvider;
import com.home.snappii.providers.NetworkService;
import com.home.snappii.view.Presenter;

import javax.inject.Singleton;

@Singleton
@dagger.Component (
        modules = { NetworkModule.class, MainModule.class }
)
public interface Component extends IComponent {
    void inject(NetworkService service);
    void inject(DataProvider dataProvider);
    void inject(Presenter presenter);
}
