package com.example.filmcatalog.films;


import com.example.filmcatalog.BasePresenter;
import com.example.filmcatalog.Main;
import com.example.filmcatalog.di.IComponent;
import com.example.filmcatalog.di.NetworkModule;
import com.example.filmcatalog.films.providers.NetworkService;

import javax.inject.Inject;

public class FilmsPresenter extends BasePresenter implements Films.Presenter {

    private Films.View view;

    @Inject
    NetworkService networkService;

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
}
