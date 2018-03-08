package com.example.filmcatalog.films.providers;



import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.films.model.Films;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

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
    public Observable<Films> getFilms(String apiKey, String language, String page) {
        return service.getApi()
                .getFilms(apiKey, language, page)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    @Override
    public Observable<Films> getFilmsWithFilter(String apiKey, String language, String page, String query) {
        return service.getApi()
                .getFilmsWithFilter(apiKey, language, page, query)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
