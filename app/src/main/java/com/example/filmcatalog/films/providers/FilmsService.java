package com.example.filmcatalog.films.providers;


import com.example.filmcatalog.films.model.Films;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmsService {

    @GET("/discover/movie")
    Observable<Films> getFilms(@Query("api-key") String apiKey);

    @GET("/search/movie")
    Observable<Films> getFilmsWithFilter(@Query("api-key") String apiKey, @Query("query") String query);
}
