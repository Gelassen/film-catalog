package com.example.filmcatalog.films;


import android.util.Log;

import com.example.filmcatalog.App;
import com.example.filmcatalog.BasePresenter;
import com.example.filmcatalog.Main;
import com.example.filmcatalog.SimpleObserver;
import com.example.filmcatalog.di.IComponent;
import com.example.filmcatalog.films.providers.FilmsProvider;

import javax.inject.Inject;

import io.reactivex.Observer;

public class FilmsPresenter extends BasePresenter implements Films.Presenter {

    private Films.View view;

    @Inject
    FilmsProvider filmsProvider;

    public FilmsPresenter(IComponent application) {
        application.inject(this);
    }

    @Override
    public void onAttachView(Main.View view) {
        if (!(view instanceof Films.View)) {
            throw new IllegalArgumentException("Wrong View implementation. Do you pass Films.View?");
        }
        this.view = (Films.View) view;
    }

    @Override
    public void onDetachView() {
        this.view = null;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onSearchMovie(String apiKey, String movie) {

    }

    @Override
    public void onPullToRefresh(String apiKey) {
        // TODO properly track subscriptions and unsubscriptions
        filmsProvider
                .getFilms(apiKey)
                .subscribe(getFilmsObserver());
    }

    private Observer<com.example.filmcatalog.films.model.Films> getFilmsObserver() {
        return new SimpleObserver<com.example.filmcatalog.films.model.Films>() {
            @Override
            public void onNext(com.example.filmcatalog.films.model.Films films) {
                super.onNext(films);
                view.onResult(films);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(App.TAG, "Failed to obtain list of data", e);
                view.onError();
            }
        };
    }
}
