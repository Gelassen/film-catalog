package com.home.snappii.providers;


import com.home.snappii.AppApplication;
import com.home.snappii.model.Data;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DataProvider implements DataService {

    private static final String DEFAULT_SEED = "abc";

    @Inject
    @Named("network")
    Scheduler subscribeScheduler;

    @Inject
    @Named("ui")
    Scheduler observeScheduler;

    NetworkService service;

    public DataProvider(AppApplication appApplication) {
        appApplication.getComponent().inject(this);
        service = new NetworkService(appApplication);
    }

    public Observable<Data> getData(int page, int results) {
        return service.getApi()
                .getData(page, results, DEFAULT_SEED)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    @Override
    public Observable<Data> getData(int page, int results, String seed) {
        return service.getApi()
                .getData(page, results, seed)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
