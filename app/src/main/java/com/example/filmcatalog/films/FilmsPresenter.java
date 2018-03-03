package com.example.filmcatalog.films;


import com.example.filmcatalog.BasePresenter;
import com.example.filmcatalog.Main;

public class FilmsPresenter extends BasePresenter implements Films.Presenter {

    private Films.View view;

    @Override
    public void onAttachView(Main.View view) {
        if (!(view instanceof Films)) {
            throw new IllegalArgumentException("Wrong View implementation. Do you pass Films.View?");
        }
        this.view = (Films.View) view;
    }

    @Override
    public void onDetachView() {
        this.view = view;
    }
}
