package com.home.banktochka.githubuserssearch.users.providers;


import com.home.banktochka.githubuserssearch.IApplication;
import com.home.banktochka.githubuserssearch.users.model.Data;
import com.home.banktochka.githubuserssearch.users.model.Item;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

public class UsersProvider {

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

    public Observable<List<Item>> getUsers(String search, String page) {
        return service.getApi()
                .getUsers(search, page)
                .flatMap(new Function<Data, ObservableSource<List<Item>>>() {
                    @Override
                    public ObservableSource<List<Item>> apply(Data data) throws Exception {
                        return Observable.just(data.getItems());
                    }
                })
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
