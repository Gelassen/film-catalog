package com.example.filmcatalog.films.providers;


import com.example.filmcatalog.films.Films;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetworkService {

    @GET("/discover")
    Observable<Films> getFilms();

    @GET("/search/search-movies")
    Observable<Films> getFilmsWithFilter();
}
