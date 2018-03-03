package com.example.filmcatalog.films.providers;



import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.films.model.Films;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FilmsProvider implements FilmsService {

    @Inject
    @Named("network")
    Scheduler subscribeScheduler;

    @Inject
    @Named("ui")
    Scheduler observeScheduler;

    private NetworkService service;

    public FilmsProvider(IApplication application) {
        application.getComponent().inject(this);
        service = new NetworkService(application);
    }

    @Override
    public Observable<Films> getFilms(String apiKey) {
        return service.getApi()
                .getFilms(apiKey)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    @Override
    public Observable<Films> getFilmsWithFilter(String apiKey, String query) {
        return service.getApi()
                .getFilmsWithFilter(apiKey, query)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
