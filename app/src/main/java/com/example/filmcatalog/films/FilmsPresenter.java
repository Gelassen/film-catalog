package com.example.filmcatalog.films;


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
    private int position = 0;

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
    public void onSearchMovie(String apiKey, String movie, boolean isFirstPage, boolean clearPreviousList, boolean hasData) {
        // TODO properly track subscriptions and unsubscriptions
        if (isFirstPage) searchPage = 1;
        onSearchMovieNextPage(apiKey, movie, searchPage, clearPreviousList, hasData);
    }

    private void onSearchMovieNextPage(String apiKey, String movie, int page, boolean clearPreviousList, final boolean hasData) {
        filmsProvider
                .getFilmsWithFilter(apiKey, defaultLang, String.valueOf(page), movie)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        searchPage++;
                        if (hasData) {
                            view.showPullToRefreshProgress();
                        } else {
                            view.showProgressPlaceholder();
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        searchPage--;
                    }
                })
                .subscribe(getFilterFilmsObserver(movie, clearPreviousList, hasData));
    }

    @Override
    public void onPullToRefresh(String apiKey, boolean hasData) {
        // TODO properly track subscriptions and unsubscriptions
        filmsProvider
                .getFilms(apiKey, defaultLang, String.valueOf(page))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        page++;
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        page--;
                    }
                })
                .subscribe(getFilmsObserver(hasData));
    }

    @Override
    public void onConfigurationChange() {
        view.onRestoreState(position);
    }

    @Override
    public void saveListState(int position) {
        this.position = position;
    }

    @Override
    public void onFilterClear() {
        page = 1;
        searchPage = 1;
    }

    @Override
    public void onTryAgain(String apiKey, String searchFilter, boolean hasData) {
        boolean isTryAgainSearch = searchFilter.length() == 0;
        if (isTryAgainSearch) {
            view.hideFailedRequest();
            view.showProgressPlaceholder();
            onPullToRefresh(apiKey, hasData);
        } else {
            // TODO I need more time to think about logic of 1s delay. RX api might have out-of-box solution
            view.hideFailedRequest();
            view.showProgressPlaceholder();
            onSearchMovie(apiKey, searchFilter,true, true, hasData);
        }
    }

    private Observer<com.example.filmcatalog.films.model.Films> getFilmsObserver(final boolean hasData) {
        return new SimpleObserver<com.example.filmcatalog.films.model.Films>() {
            @Override
            public void onNext(com.example.filmcatalog.films.model.Films films) {
                super.onNext(films);
                view.onResult(films, films.getResults().size() < AMOUNT_IN_REQUEST);
                view.showList();
                resetState();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (hasData) {
                    view.showError();
                    view.hidePullToRefreshProgress();
                } else {
                    view.hideProgressPlaceholder();
                    view.showOnFailedRequest();
                    view.hideList();
                }
            }
        };
    }

    private Observer<com.example.filmcatalog.films.model.Films> getFilterFilmsObserver(final String query,
                                                                                       final boolean clearPreviousList,
                                                                                       final boolean hasData) {
        return new SimpleObserver<com.example.filmcatalog.films.model.Films>() {
            @Override
            public void onNext(com.example.filmcatalog.films.model.Films films) {
                super.onNext(films);
                boolean isNotFound = films.getResults().size() == 0;
                if (isNotFound) {
                    view.onFilterResult(films, true);
                    view.showFilmsNotFound(query);
                } else {
                    if (clearPreviousList) {
                        view.onClearList();
                    }
                    view.onFilterResult(films, films.getResults().size() < AMOUNT_IN_REQUEST);
                }
                view.showList();
                resetState();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (hasData) {
                    view.showError();
                    view.hidePullToRefreshProgress();
                } else {
                    view.hideProgressPlaceholder();
                    view.showOnFailedRequest();
                    view.hideList();
                }
            }
        };
    }

    private void resetState() {
        view.hidesError();
        view.hidesFilmsNotFound();
        view.hideFailedRequest();
        view.hideProgressPlaceholder();
        view.hidePullToRefreshProgress();
    }
}
