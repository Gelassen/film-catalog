package com.example.filmcatalog.films.providers;


import com.example.filmcatalog.films.model.Films;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmsService {

    @GET("/3/discover/movie")
    Observable<Films> getFilms(@Query("api_key") String apiKey);

    @GET("/3/search/movie")
    Observable<Films> getFilmsWithFilter(@Query("api_key") String apiKey, @Query("query") String query);
}
