package com.example.filmcatalog.di;


import com.example.filmcatalog.films.FilmsPresenter;
import com.example.filmcatalog.films.providers.FilmsProvider;
import com.example.filmcatalog.films.providers.NetworkService;

public interface IComponent {
    void inject(NetworkService service);

    void inject(FilmsPresenter filmsPresenter);

    void inject(FilmsProvider provider);
}
