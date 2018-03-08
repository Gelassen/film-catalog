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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FilmsPresenter extends BasePresenter implements Films.Presenter {

    private static final int AMOUNT_IN_REQUEST = 20;

    private Films.View view;

    @Inject
    FilmsProvider filmsProvider;

    private String defaultLang = "ru-RU";
    private int page = 1;
    private int searchPage = 1;

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
    public void onSearchMovie(String apiKey, String movie, boolean isFirstPage) {
        // TODO properly track subscriptions and unsubscriptions
        if (isFirstPage) searchPage = 0;
        onSearchMovieNextPage(apiKey, movie, searchPage);
    }

    private void onSearchMovieNextPage(String apiKey, String movie, int page) {
        filmsProvider
                .getFilmsWithFilter(apiKey, defaultLang, String.valueOf(page), movie)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgressPlaceholder();
                        searchPage++;
//                        com.example.filmcatalog.films.model.Films films = new com.example.filmcatalog.films.model.Films();
//                        films.setResults(new ArrayList<Result>());
//                        view.onResult(films);
                    }
                })
                .subscribe(getFilterFilmsObserver(movie));
    }

    @Override
    public void onPullToRefresh(String apiKey) {
        // TODO properly track subscriptions and unsubscriptions
        filmsProvider
                .getFilms(apiKey, defaultLang, String.valueOf(page))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgressPlaceholder();
                        page++;
                    }
                })
                .subscribe(getFilmsObserver());
    }

    private Observer<com.example.filmcatalog.films.model.Films> getFilmsObserver() {
        return new SimpleObserver<com.example.filmcatalog.films.model.Films>() {
            @Override
            public void onNext(com.example.filmcatalog.films.model.Films films) {
                super.onNext(films);
                view.onResult(films, films.getResults().size() < AMOUNT_IN_REQUEST);
                resetState();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(App.TAG, "Failed to obtain list of data", e);
                view.hideProgressPlaceholder();
                view.showError();
            }
        };
    }

    private Observer<com.example.filmcatalog.films.model.Films> getFilterFilmsObserver(final String query) {
        return new SimpleObserver<com.example.filmcatalog.films.model.Films>() {
            @Override
            public void onNext(com.example.filmcatalog.films.model.Films films) {
                super.onNext(films);
                boolean isNotFound = films.getResults().size() == 0;
                if (isNotFound) {
                    view.onFilterResult(films, true);
                    view.showFilmsNotFound(query);
                } else {
                    view.onFilterResult(films, films.getResults().size() < AMOUNT_IN_REQUEST);
                }
                view.hideProgressPlaceholder();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(App.TAG, "Failed to obtain list of data", e);
                view.hideProgressPlaceholder();
                view.showError();
            }
        };
    }

    private void resetState() {
        view.hidesError();
        view.hidesFilmsNotFound();
        view.hideProgressPlaceholder();
    }
}
