package com.home.snappii.di;

import com.home.snappii.providers.DataProvider;
import com.home.snappii.providers.NetworkService;
import com.home.snappii.view.DetailedActivity;
import com.home.snappii.view.DetailedPresenter;
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
    void inject(DetailedActivity detailedActivity);
    void inject(DetailedPresenter detailedPresenter);
}
