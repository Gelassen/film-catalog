package com.home.banktochka.githubuserssearch.users.providers;


import com.home.banktochka.githubuserssearch.IApplication;
import com.home.banktochka.githubuserssearch.users.model.Data;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class UsersProvider implements UserService {

    @Inject
    @Named("network")
    Scheduler subscribeScheduler;

    @Inject
    @Named("ui")
    Scheduler observeScheduler;

    private NetworkService service;

    public UsersProvider(IApplication application) {
        application.getComponent().inject(this);
        service = new NetworkService(application);
    }

    @Override
    public Observable<Data> getUsers(String search, String page) {
        return service.getApi()
                .getUsers(search, page)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
